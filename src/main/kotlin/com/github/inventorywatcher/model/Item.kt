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
data class Item constructor(
        var _id: String?,
        var name: String?,
        var barcode: String?,
        var quantity: Double?,
        var unit: String?,
        var bestBefore: LocalDate?,
        var tags: List<String>?,
        var notification: Notification?) : Validatable, JsonConvertable {

    companion object Message{
        val NAME_MUST_NOT_BE_NULL = "Name must not be null!"
    }

    constructor() : this(null,null,null,null,null,null,null,null)

    constructor(item: Item) : this(item._id, item.name, item.barcode, item.quantity, item.unit, item.bestBefore, item.tags, item.notification)

    constructor(json: JsonObject) : this(Json.decodeValue(json.encode(), Item::class.java))

    constructor(json: String) : this(Json.decodeValue(json, Item::class.java))


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