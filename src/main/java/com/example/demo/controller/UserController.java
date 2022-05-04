package com.example.demo.controller;

import com.example.demo.model.DocType;
import com.example.demo.model.User;
import com.example.demo.service.DocTypeService;
import com.example.demo.service.UserService;
import io.reactivex.Maybe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    DocTypeService docTypeService;

    @Operation(summary = "Metodo para insertar un Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }) })
    @PostMapping("/insert-user")
    public Maybe<ResponseEntity<User>> add(@RequestBody User user) {
        return Maybe.just(user)
                .map(e -> docTypeService.findById(e.getDocType().getId()))
                .map(f -> {
                    if (f.getId() == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    userService.save(user);
                    return new ResponseEntity<>(HttpStatus.OK);
                });
    }

    @Operation(summary = "Metodo para la actualizacion de un Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuario no encotrado.",
                    content = @Content) })
    @PutMapping("/update-user")
    public ResponseEntity<User> mod(@RequestBody User user) {
        if (userService.getOne(user.getId()).getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (docTypeService.findById(user.getDocType().getId()).getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(userService.update(user));
        }
    }

    @Operation(summary = "Metodo para obtener todos los Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios Encotnrados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) })})
    @GetMapping("/getAllUsers")
    public Maybe<ResponseEntity<List<User>>> getAll(){
        return Maybe.just(ResponseEntity.ok(userService.getAll()));
    }

    @Operation(summary = "Metodo para eliminar un Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuario no encotrado.",
                    content = @Content) })
    @DeleteMapping("/delete-user/{id}")
    public Maybe<ResponseEntity<Void>> delete(@PathVariable Integer id){
        return Maybe.just(id)
                .map(e -> userService.getOne(e))
                .map(f -> {
                    if (f.getId() == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    userService.deleteUser(f.getId());
                    return new ResponseEntity<>(HttpStatus.OK);
                });
    }

}
