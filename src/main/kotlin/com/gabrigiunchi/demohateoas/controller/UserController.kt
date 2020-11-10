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

class ModifyNameRequest(val name: String)
data class ModifyUserRequest(val id: String, val name: String, val surname: String)

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUsers(): ResponseEntity<CollectionModel<User>> {
        val users = this.userService.users.map {
            it.add(linkTo<UserController> {
                methodOn(UserController::class.java).getUserById(it.id)
            }.withSelfRel())
            it
        }
        return ResponseEntity.ok(
            CollectionModel.of(users).add(linkTo<UserController> {
                methodOn(UserController::class.java).getUsers()
            }.withSelfRel())
        )
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<EntityModel<User>> {
        val user = this.userService.getUserById(id)
        val selfLink = linkTo<UserController> { methodOn(UserController::class.java).getUserById(id) }
            .withSelfRel()
            .andAffordance(afford<UserController> {
                methodOn(UserController::class.java).modifyUserName(
                    id,
                    ModifyNameRequest("gabriele")
                )
            })
            .andAffordance(afford<UserController> {
                methodOn(UserController::class.java).deleteUser(id)
            })
            .andAffordance(afford<UserController> {
                methodOn(UserController::class.java).modifyUser(id, ModifyUserRequest("1", "gabriele", "giunchi"))
            })
        val next = linkTo<UserController> {
            methodOn(UserController::class.java).getUserById(id.toInt().plus(1).toString())
        }.withRel("next")
        return ResponseEntity.ok(EntityModel.of(user, selfLink, next))
    }

    @PutMapping("/{id}")
    fun modifyUser(@PathVariable id: String, @RequestBody request: ModifyUserRequest): ResponseEntity<User> {
        val user = this.userService.getUserById(id)
        user.add(
            linkTo<UserController> {
                methodOn(UserController::class.java).getUserById(id)
            }.withSelfRel()
                .andAffordance(afford<UserController> { methodOn(UserController::class.java).deleteUser(id) })
        )

        return ResponseEntity.ok(user)
    }

    @PatchMapping("/{id}")
    fun modifyUserName(@PathVariable id: String, @RequestBody request: ModifyNameRequest): ResponseEntity<User> {
        val user = this.userService.getUserById(id)
        user.name = request.name
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Nothing> {
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}