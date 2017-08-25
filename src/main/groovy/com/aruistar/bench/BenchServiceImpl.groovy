package com.aruistar.bench

import com.aruistar.other.AruisLog
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

class BenchServiceImpl implements BenchService, AruisLog {

    Vertx vertx

    BenchServiceImpl(Vertx vertx, JsonObject config, Handler<AsyncResult<BenchService>> readyHandler) {

        this.vertx = vertx
        readyHandler.handle(Future.succeededFuture(this));

    }


}
