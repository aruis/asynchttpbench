package com.aruistar.entity

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

@DataObject(generateConverter = true)
class BenchForm {


    String url
    Boolean keepAlive
    int maxPoolSize
    int timeout
    int allRequestTimes
    String uuid

    String getUuid() {
        return uuid
    }

    void setUuid(String uuid) {
        this.uuid = uuid
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    Boolean getKeepAlive() {
        return keepAlive
    }

    void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive
    }

    int getMaxPoolSize() {
        return maxPoolSize
    }

    void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize
    }

    int getTimeout() {
        return timeout
    }

    void setTimeout(int timeout) {
        this.timeout = timeout
    }

    int getAllRequestTimes() {
        return allRequestTimes
    }

    void setAllRequestTimes(int allRequestTimes) {
        this.allRequestTimes = allRequestTimes
    }

    BenchForm() {
    }

    BenchForm(JsonObject user) {
        BenchFormConverter.fromJson(user, this)
    }

    JsonObject toJson() {
        def json = new JsonObject()
        BenchFormConverter.toJson(this, json)
        return json
    }

    @Override
    String toString() {
        return toJson()
    }
}
