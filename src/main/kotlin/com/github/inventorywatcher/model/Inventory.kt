package com.github.inventorywatcher.model

import com.fasterxml.jackson.annotation.JsonInclude
import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import java.util.*

/**
 * Created by hunyadym on 2016. 02. 17..
 */

@DataObject
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Inventory constructor(var name: String?, var items: List<Item>?) : Validatable, JsonConvertable {


    companion object Message {
        val NAME_MUST_NOT_BE_NULL = "Name must not be null!"
    }


    constructor() : this(null, null)

    constructor(inventory: Inventory) : this(inventory.name, inventory.items)

    constructor(json: JsonObject) : this(Json.decodeValue(json.encode(), Inventory::class.java))

    constructor(json: String) : this(Json.decodeValue(json, Inventory::class.java))


    override fun validate(): List<String> {
        val errors = ArrayList<String>()
        if (items == null) {
            errors.add(Inventory.NAME_MUST_NOT_BE_NULL)
        }
        return errors
    }

    override fun toJson(): JsonObject {
        return JsonObject(this.toJsonString())
    }


}