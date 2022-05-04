package com.example.demo.controller;

import com.example.demo.model.DocType;
import com.example.demo.model.User;
import com.example.demo.service.DocTypeService;
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
public class DocTypeController {

    @Autowired
    private DocTypeService docTypeService;

    @Operation(summary = "Metodo para insertar un Documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento registrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocType.class)) }) })
    @PostMapping("/insert-document")
    public Maybe<ResponseEntity<DocType>> add(@RequestBody DocType docType) {
        return Maybe.just
                (ResponseEntity.ok(docTypeService.save(docType)));
    }

    @Operation(summary = "Metodo para la actualizacion de un Documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento actualizado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocType.class)) }),
            @ApiResponse(responseCode = "404", description = "Documento no encotrado.",
                    content = @Content) })
    @PutMapping("/update-document/{id}")
    public Maybe<ResponseEntity<Void>> mod(@PathVariable Integer id,
                                              @RequestBody DocType docType) {
        return Maybe.just(id)
                .map(e -> docTypeService.findById(e))
                .map(f -> {
                    if (f.getId() == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    f.setDescription(docType.getDescription());
                    docTypeService.update(f);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                });
    }

    @Operation(summary = "Metodo para obtener todos los Documentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos Encontrados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocType.class)) })})
    @GetMapping("/getAllDocuments")
    public Maybe<ResponseEntity<List<DocType>>> getAll(){
        return Maybe.just(ResponseEntity.ok(docTypeService.getAll()));
    }

    @Operation(summary = "Metodo para eliminar un Documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento eliminado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Documento no encotrado.",
                    content = @Content) })
    @DeleteMapping("/delete-document/{id}")
    public Maybe<ResponseEntity<Void>> delete(@PathVariable Integer id){
        return Maybe.just(id)
                .map(e -> docTypeService.findById(e))
                .map(f -> {
                    if (f.getId() == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    docTypeService.removeById(f.getId());
                    return new ResponseEntity<>(HttpStatus.OK);
                });
    }
}
