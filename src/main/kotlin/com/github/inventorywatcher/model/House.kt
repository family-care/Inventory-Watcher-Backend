package com.github.inventorywatcher.model

import com.fasterxml.jackson.annotation.JsonInclude
import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import java.util.*

/**
 * Created by hunyadym on 2016. 02. 14..
 */

@DataObject
@JsonInclude(JsonInclude.Include.NON_NULL)
data class House constructor(
        var _id: String?,
        var name: String?,
        var members: List<Member>?,
        var inventory: List<Inventory>?
) : Validatable, JsonConvertable {

    companion object Message {
        val NAME_MUST_NOT_BE_NULL = "Name must not be null!"
    }

    constructor() : this(null, null, null, null)

    constructor(house: House) : this(house._id, house.name, house.members, house.inventory)

    constructor(json: JsonObject) : this(Json.decodeValue(json.encode(), House::class.java))

    constructor(json: String) : this(Json.decodeValue(json, House::class.java))


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
