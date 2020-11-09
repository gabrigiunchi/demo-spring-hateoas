package com.gabrigiunchi.demohateoas.controller

import com.gabrigiunchi.demohateoas.model.User
import com.gabrigiunchi.demohateoas.service.UserService
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.afford
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class ModifyNameRequest(val name: String)

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUsers(): CollectionModel<User> {
        val users = this.userService.users
        users.forEach {
            it.add(linkTo<UserController> {
                methodOn(UserController::class.java).getUserById(it.id)
            }.withSelfRel())
        }
        return CollectionModel.of(users).add(linkTo<UserController> {
            methodOn(UserController::class.java).getUsers()
        }.withSelfRel())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): EntityModel<User> {
        val user = this.userService.getUserById(id)
        user.add(linkTo<UserController> {
            methodOn(UserController::class.java).getUserById(id.toInt().plus(1).toString())
        }.withRel("next"))

        val selfLink = linkTo<UserController> { methodOn(UserController::class.java).getUserById(id) }
            .withSelfRel()
            .andAffordance(afford<UserController> {
                methodOn(UserController::class.java).modifyUserName(
                    id,
                    EntityModel.of(ModifyNameRequest("gabriele"))
                )
            })
            .andAffordance(afford<UserController> {
                methodOn(UserController::class.java).modifyUserName(
                    id, EntityModel.of(
                        ModifyNameRequest("francesco")
                    )
                )
            })
        return EntityModel.of(user, selfLink)
    }

    @PatchMapping("/{id}")
    fun modifyUserName(
        @PathVariable id: String,
        @RequestBody request: EntityModel<ModifyNameRequest>
    ): ResponseEntity<User> {
        val user = this.userService.getUserById(id)
        user.name = request.content?.name ?: throw Exception("bad request")
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Nothing> {
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}