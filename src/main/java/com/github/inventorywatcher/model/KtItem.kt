package com.github.inventorywatcher.model

import com.fasterxml.jackson.annotation.JsonInclude
import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import java.time.LocalDate
import java.util.*

/**
 * Created by hunyadym on 2016. 02. 14..
 */

@DataObject
@JsonInclude(JsonInclude.Include.NON_NULL)
data class KtItem constructor(val _id: String, val name: String, val barcode: String, val quantity: Double, val unit: String, val bestBefore: LocalDate, val tags: ArrayList<String>, val notification: Notification) : Validatable, JsonConvertable {
    val NAME_MUST_NOT_BE_NULL = "Name must not be null!"

    constructor(item: KtItem) : this(item._id, item.name, item.barcode, item.quantity, item.unit, item.bestBefore, item.tags, item.notification)

    constructor(json: JsonObject) : this(Json.decodeValue(json.encode(), KtItem::class.java))

    constructor(json: String) : this(Json.decodeValue(json, KtItem::class.java))

    override fun validate(): List<String> {
        val errors = ArrayList<String>()
        if (name == null) {
            errors.add(NAME_MUST_NOT_BE_NULL)
        }
        return errors
    }

    override fun toJson(): JsonObject {
        return JsonObject(this.toJsonString())
    }
}