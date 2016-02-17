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
data class User constructor(var _id: String?, var right: String?) : Validatable, JsonConvertable {


    companion object Message {
        val ID_MUST_NOT_BE_NULL = "ID must not be null!"
    }

    constructor() : this(null, null)

    constructor(user: User) : this(user._id, user.right)

    constructor(json: JsonObject) : this(Json.decodeValue(json.encode(), User::class.java))

    constructor(json: String) : this(Json.decodeValue(json, User::class.java))


    override fun validate(): List<String> {
        val errors = ArrayList<String>()
        if (_id == null) {
            errors.add(User.ID_MUST_NOT_BE_NULL)
        }
        return errors
    }

    override fun toJson(): JsonObject {
        return JsonObject(this.toJsonString())
    }


}
