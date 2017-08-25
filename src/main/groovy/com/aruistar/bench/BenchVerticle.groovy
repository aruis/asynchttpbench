package com.aruistar.bench

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.serviceproxy.ProxyHelper

class BenchVerticle extends AbstractVerticle {

    @Override
    void start(Future<Void> startFuture) throws Exception {


        BenchService.create(vertx, null, { ready ->

            if (ready.succeeded()) {
                ProxyHelper.registerService(BenchService.class, vertx, ready.result(), "aruistar.bench")

                super.start(startFuture)
            } else {
                startFuture.fail(ready.cause());
            }
        })


    }
}
