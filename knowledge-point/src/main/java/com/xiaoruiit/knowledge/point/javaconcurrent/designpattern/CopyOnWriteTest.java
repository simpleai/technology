package com.xiaoruiit.knowledge.point.javaconcurrent.designpattern;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 并发设计模式29
 */
public class CopyOnWriteTest {
    public static void main(String[] args) {
        RouterRelation routerRelation = new RouterRelation();

        Router router = new Router("1", 1, "1");
        routerRelation.add(router);

        Router router2 = new Router("2", 1, "1");
        routerRelation.add(router2);
    }


}

final class Router {
    private final String ip;

    private final Integer port;

    private final String interfaceName;


    public Router(String address, Integer port, String interfaceName) {
        this.ip = address;
        this.port = port;
        this.interfaceName = interfaceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Router router = (Router) o;
        return Objects.equals(ip, router.ip) && Objects.equals(port, router.port) && Objects.equals(interfaceName, router.interfaceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, interfaceName);
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getInterfaceName() {
        return interfaceName;
    }
}

class RouterRelation {
    /**
     * key:接口名
     * value:Router集合
     */
    ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> map = new ConcurrentHashMap<>();

    public Set<Router> get(String interfaceName){
        return map.get(interfaceName);
    }

    public void add(Router router){
        CopyOnWriteArraySet<Router> routers = map.computeIfAbsent(router.getInterfaceName(), r -> new CopyOnWriteArraySet<>());

        // 此时存在map中有key，value没有值

        routers.add(router);
    }

    public void remove(Router router){
        CopyOnWriteArraySet<Router> routers = map.get(router.getInterfaceName());

        if (routers != null){
            routers.remove(router);
        }
    }
}
