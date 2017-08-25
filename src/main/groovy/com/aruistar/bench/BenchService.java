package com.aruistar.bench;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface BenchService {


    static BenchService create(Vertx vertx, JsonObject dbConfig, Handler<AsyncResult<BenchService>> readyHandler) {
        return new BenchServiceImpl(vertx, dbConfig, readyHandler);
    }

    static BenchService createProxy(Vertx vertx, String address) {
        return new BenchServiceVertxEBProxy(vertx, address);
    }
}
