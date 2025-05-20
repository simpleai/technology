# 记录采购订单从产生到被收货前关键操作。

通过领域事件记录采购订单操作日志.

多语环境

## 存储简要流程

1. 采购订单、发货单发出异步领域事件
2. 采购订单应用层监听领域事件，将领域事件转化为采购订单操作日志
3. 调用公共服务 RPC接口，存储采购订单操作日志到公用的操作日志表

## 查询日志
目前存储内容简单。直接从公共服务查询。

日志中有json查询出来需要转化，可以在各领域通过RPC查询并转化，再给到前端

## 记录采购订单操作日志业务类型

- {#组织类型}{#来源}采购订单创建；
- {#组织类型}{#来源}采购强配订单创建；
- {#组织类型}采购订单修改；
- {#组织类型}采购订单审核；
- {#组织类型}采购订单反审核；
- {#组织类型}采购订单复核；
- {#组织类型}采购订单取消复核；
- {#组织类型}采购订单付款；
- {#组织类型}采购订单确认收款；
- {#组织类型}{#配送类型}采购订单接单；
- {#组织类型}{#配送类型}采购订单取消接单；
- {#组织类型}驳回；


- {#组织类型}审批可发货；
- {#组织类型}{#配送类型}发货；
- {#组织类型}{#配送类型}取消发货；
- {#组织类型}{#配送类型}中止发货；


## 操作日志实体
```java
public class DocumentOperationLogE implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单据号
     */
    private String docNo;

    /**
     * 单据类型
     */
    private Short docType;

    /**
     * 操作类型。各个业务类型对应不同的操作类型，业务方自定义
     */
    private String operationType;

    /**
     * 操作人
     */
    private String operationBy;

    /**
     * 操作时间
     */
    private Long operationTime;

    /**
     * 操作详情 JSONB
     */
    private String operationDetail;
}
```

## 领域事件定义

### 采购订单领域事件类型

```java
ADD((short) 10, "新增"),
ADD_PLACE_ORDER_FOR_STORE((short) 11, "新增-代店下单"),
ADD_FROM_REQUEST((short) 12, "新增-来自采购申请单"), // 在采购订单中发送领域事件.领域事件应该由“事件真正发生的聚合根”来发布，而不是“触发这个事件的对象”。
MODIFY((short) 20, "修改"),
AUDIT((short) 30, "审核"),
UNAUDIT((short) 40, "反审核"),
PAYMENT((short) 50, "付款"),
RECEIPT((short) 60, "收款"),
ACCEPT((short) 70, "接单"),
CANCEL_ACCEPT((short) 80, "取消接单"),
REVIEW((short) 90, "复核"),
CANCEL_REVIEW((short) 100, "取消复核"),
REJECTED((short) 110, "驳回"),
;
```
### 发货单领域事件类型

```java
ADD(10, "新增"),
MODIFY(20, "修改"),
APPROVE_SHIPMENT(30, "审批可发货"),
SHIP(40, "发货"),
CANCEL_SHIPMENT(50, "取消发货"),
TERMINATE_SHIPMENT(60, "中止发货"),
DELETED(70, "删除"),
UNKNOWN(0, "未知"),
;
```

### 通用发布事件实体定义

```java
/**
 * 通用发布事件实体
 * @param <T> 事件类型枚举
 */
public class OrderEventE<T> implements Serializable {
    /**
     * 操作人、操作组织、所属租户
     */
    private EmployeeInfo employeeInfo;

    private List<OrderBrief> orderBriefList;
    /**
     * 操作时间
     */
    private Long operationTime;
    /**
     * 组合事件。同一时刻多个不同事件同时发生，发出的时候需要有序。组合事件可解决同一时刻多个不同事件同时发生，分别发送异步消费时的乱序问题
     */
    private List<T> eventTypeList;

    @Data
    @Accessors(chain = true)
    public static class OrderBrief {
        private String docNo;
        private String sourceDocNo;
        private String sourceDocType;
    }
}
```

## 涉及技术栈
Java
RabbitMQ
Redis
事务钩子

## 领域事件发布

```java
/**
 * 发货单领域事件发布
 */
public void afterTransactionSendEvent(List<DeliveryNoteAggregate> aggregateList, Long corporationId, List<DeliveryOrderEventTypeEnum> eventTypeEnumList) {
    try {
        log.info("准备发送发货单事件，事件类型:{}", com.alibaba.fastjson2.JSON.toJSONString(eventTypeEnumList));
        List<OrderEventE.OrderBrief> orderBriefList = aggregateList.stream().map(aggregate -> {
            OrderEventE.OrderBrief orderBrief = new OrderEventE.OrderBrief()
                    .setDocNo(aggregate.getDocInfo().getDocNo())
                    .setSourceDocNo(aggregate.getUpStreamInfo().getSourceDocNos());
            return orderBrief;
        }).collect(toList());

        OrderEventE orderEventE = new OrderEventE()
                .setOrderBriefList(orderBriefList)
                .setCorporationId(corporationId)
                .setOperationTime(Instant.now().toEpochMilli())
                .setEventTypeList(eventTypeEnumList)
                .setEmployeeInfo(EmployeeContext.getEmployee());

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {// 事务提交后发送MQ 回调方法内需要 try catch，外层try catch对回调方法不生效
                    try {
                        deliveryOrderEventPublish.publish(orderEventE);
                    } catch (Exception e) {
                        log.error("发货单事件发送失败", e);
                    }
                }
            });
        } else {
            deliveryOrderEventPublish.publish(orderEventE);
        }
    } catch (Exception e) {
        log.error("发货单事件发送失败", e);
    }
}

/**
 * 发货单领域事件发布
 */
@Component
@Slf4j
public class DeliveryOrderEventPublishImpl implements DeliveryOrderEventPublish {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${mq.deliveryOrderExchange:inventory.deliveryOrder.event.exchange}")
    private String deliveryOrderExchange;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * topic模式发送。不同领域事件用 routingKey 区分
     * @param eventE
     */
    @Override
    public void publish(OrderEventE eventE) {
        log.info("DeliveryOrderEventPublishImpl#publish eventE{}",JSONObject.toJSONString(eventE));

        String redisKey = "deliveryOrderEvent:sent:" + eventE.getEventId();
        if (redisUtil.hasKey(redisKey)){// 分布式事务可能会导致重复发送
            log.warn("消息已发送过，跳过发送, eventId={}", eventE.getEventId());
            return;
        }

        // 加入来源类型；方便采购订单不订阅非采购订单的发货单事件
        String preRoutingKey;
        if ("SALES_ORDER".equals(item.getSourceType())) {
            routingKey = "sales";
        } else if ("PURCHASE_ORDER".equals(item.getSourceType())) {
            routingKey = "purchase";
        } else {
            routingKey = "manual";
        }
        
        String routingKey = preRoutingKey + eventE.getEventTypeList().stream()
                .map(DeliveryOrderEventTypeEnum::getRoutingKeyPart)
                .sorted()
                .collect(Collectors.joining("-and-"));

        rabbitTemplate.convertAndSend(deliveryOrderExchange, routingKey, JSON.toJSONString(eventE));
        log.info("发送成功:{}", JSON.toJSONString(eventE));

        redisUtil.set(redisKey, 1L, 10L);
    }

}
```

## 消费领域事件，保存为操作日志

```java
/**
 * 发货单事件监听
 * @author hanxiaorui
 */
@Component
@RequiredArgsConstructor
public class DeliveryOrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(DeliveryOrderEventListener.class);

    @Autowired
    private DeliveryOrderEventHandler handler;

    /**
     * 消费发货单事件，记录采购订单操作日志
     * @param message
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${mq.purchaseOrderOperationLog:order.purchaseOrder.log.queue-scm}",
                            autoDelete = "false",
                            durable = "true"
                    ),
                    exchange = @Exchange(value = "${mq.deliveryOrderExchange:inventory.deliveryOrder.event.exchange}",
                            type = ExchangeTypes.TOPIC),
                    key = "#" // 先不精细化订阅，1.发货事件可能变 2.目前无需求(以后非采购单来源的发货单事件，不需要记录采购订单操作日志)
            ), ackMode = "AUTO")

    public void handlerDeliveryOrderEvent(Message message) throws Exception{
        MdcUtil.putTraceId(IdUtil.fastUUID());
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("inventory.schedule.listener.DeliveryOrderListener.handlerDeliveryOrderEvent Message message = {}",messageBody);
        try {
            OrderEventE event = JSON.parseObject(messageBody, OrderEventE.class);
            handler.handlerMessage(event);
        }catch (Exception e){
            log.info("handlerCorporationCreateEvent Message error",e);
            
            // 抛异常会导致 MQ 一直重试。需要增加死信队列，才能配置重试次数；
        }
    }
}

@Slf4j
@Component
public class DeliveryOrderEventHandler {

    @Autowired
    private PurchaseOrderDomainService domainService;

    @Autowired
    private RedisUtil redisUtil;

    public void handlerMessage(OrderEventE eventE) {
        EmployeeContext.setEmployee(eventE.getEmployeeInfo());// 设置ThreadLocal

        // 幂等
        String redisKey = "deliveryOrderEvent:reveive:" + eventE.getEventId();

        if (redisUtil.hasKey(redisKey)) {
            log.warn("消息已消费, eventId={}", eventE.getEventId());
            return;
        }

        domainService.handlerDeliveryOrderEvent4writePurchaseOrderOperationLog(eventE);

        // 2. 发送成功后打幂等标记
        redisUtil.set(redisKey, "1", 10L);
    }
}
```

领域事件转化为操作日志实体
```java
public void handlerDeliveryOrderEvent4writePurchaseOrderOperationLog(OrderEventE eventE) {
        Map<String, List<OrderEventE.OrderBrief>> collect =
                eventE.getOrderBriefList().stream().filter(e -> e.getSourceDocNo() != null)// 先在这过滤无来源单
                        .collect(Collectors.groupingBy(OrderEventE.OrderBrief::getSourceDocNo));

        List<DocumentOperationLogCmd> documentOperationLogCmdList = new ArrayList<>();

        String optBy = eventE.getEmployeeInfo().getUserName();
        if ("默认用户".equals(optBy.trim())) {
            optBy = "system";
        }

        for (String purchaseDocNo : collect.keySet()) {
            List<OrderEventE.OrderBrief> orderBriefs = collect.get(purchaseDocNo);

            for (DeliveryOrderEventTypeEnum eventTypeEnum : eventE.getEventTypeList()) {
                PurchaseOperationTypeLogEnum operationType = null;

                switch (eventTypeEnum) {
                    case APPROVE_SHIPMENT:
                        operationType = PurchaseOperationTypeLogEnum.DISTRIBUTION_CENTER_APPROVE_SHIPMENT;
                        break;
                    case CANCEL_SHIPMENT:
                        if (Objects.equals(OrgTypeEnum.DISTRIBUTION_CENTER.getCode(), eventE.getEmployeeInfo().getOrgType())) {
                            operationType = PurchaseOperationTypeLogEnum.DISTRIBUTION_CENTER_CANCEL_SHIPMENT;
                        } else if (OrgTypeEnum.EXTERNAL_SUPPLIER.getCode().equals(EmployeeContext.getEmployee().getOrgType())) {
                            operationType = PurchaseOperationTypeLogEnum.SUPPLIER_COLLABORATIVE_CANCEL_SHIPMENT;
                        } else {// 供应商协同外部供应商 组织类型是0，前端未传入
                            log.warn("未知的orgType: {}", eventE.getEmployeeInfo().getOrgType());
                            operationType = PurchaseOperationTypeLogEnum.SUPPLIER_COLLABORATIVE_CANCEL_SHIPMENT;
                        }
                        break;
                    case MODIFY:
                        operationType = PurchaseOperationTypeLogEnum.DISTRIBUTION_CENTER_MODIFY;
                        break;
                    case SHIP:
                        if (Objects.equals(OrgTypeEnum.DISTRIBUTION_CENTER.getCode(), eventE.getEmployeeInfo().getOrgType())) {
                            operationType = PurchaseOperationTypeLogEnum.DISTRIBUTION_CENTER_SHIPMENT;
                        } else if (OrgTypeEnum.EXTERNAL_SUPPLIER.getCode().equals(EmployeeContext.getEmployee().getOrgType())) {
                            operationType = PurchaseOperationTypeLogEnum.SUPPLIER_COLLABORATIVE_SHIPMENT;
                        } else {// 供应商协同外部供应商 组织类型是0，前端未传入
                            log.warn("未知的orgType: {}", eventE.getEmployeeInfo().getOrgType());
                            operationType = PurchaseOperationTypeLogEnum.SUPPLIER_COLLABORATIVE_SHIPMENT;
                        }
                        break;
                    case TERMINATE_SHIPMENT:
                        operationType = PurchaseOperationTypeLogEnum.DISTRIBUTION_CENTER_TERMINATE_SHIPMENT;
                        break;
                    default:
                        log.warn("未识别的事件类型: {}", eventTypeEnum);
                        operationType = PurchaseOperationTypeLogEnum.UNKNOWN;
                        break;
                }

                if (operationType == PurchaseOperationTypeLogEnum.UNKNOWN) {
                    continue;
                }

                PurchaseOrderOperationLogDetailE purchaseOrderOperationLogDetailE = new PurchaseOrderOperationLogDetailE().setDeliveryDocNos(orderBriefs.stream().map(OrderEventE.OrderBrief::getDocNo).collect(Collectors.toList()));

                DocumentOperationLogCmd documentOperationLogCmd = DocumentOperationLogCmd.builder()
                        .docNo(purchaseDocNo)
                        .docType(DocTypeEnum.PURCHASE_ORDER.getCode())
                        .operationType(String.valueOf(operationType))
                        .operationBy(eventE.getEmployeeInfo().getUserName())
                        .operationTime(eventE.getOperationTime())
                        .operationDetail(JSON.toJSONString(purchaseOrderOperationLogDetailE))
                        .build();

                documentOperationLogCmdList.add(documentOperationLogCmd);
            }
        }

        if (CollectionUtils.isEmpty(documentOperationLogCmdList)) {
            log.warn("有未识别的事件类型或无来源单号");
            return ;
        }

        documentOperationLogGateway.batchInsert(documentOperationLogCmdList);
        log.info("发货单事件写入采购订单操作日志成功");
    }
```