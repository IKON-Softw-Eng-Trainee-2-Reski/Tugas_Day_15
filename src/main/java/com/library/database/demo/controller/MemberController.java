package com.library.database.demo.controller;

import com.library.database.demo.entity.Book;
import com.library.database.demo.entity.Member;
import com.library.database.demo.entity.ResponseMessage;
import com.library.database.demo.exception.EmptyTableException;
import com.library.database.demo.service.BookService;
import com.library.database.demo.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class MemberController {
    @Autowired(required = false)
    MemberService memberService;
//    org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog("MemberController");

    // start GET methods //
    @Operation(summary = "Get all Member")
    @GetMapping(path="v1/member")
    public ResponseEntity<List<Member>> getMember() {
        log.debug("[user] v1/member is called");
        try {
            ResponseEntity<List<Member>> response = new ResponseEntity<>(memberService.getMember(), HttpStatus.OK);
            log.warn("[user] response length is: "+response.getBody().size());

            if (response.getBody().size() < 1) {
                throw new EmptyTableException("Data doesn't exist in table!!!");
            } else {
                return response;
            }
        } catch (InvalidDataAccessResourceUsageException e) { //db exists, tbl doesn't exist
            log.error("Data doesn't exist in table member v1/member" + e);
            return new ResponseEntity<>(HttpStatus.GONE);
        } catch (CannotCreateTransactionException e) { //during springboot run, db is deleted
            log.warn("[user] exception message is: "+e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        } catch (EmptyTableException e) { //tbl exists but is empty
            log.warn("[user] exception message is: "+e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) { //any other exceptions
            log.warn("[user] exception message is: "+e);
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping(path="/v1/member/{memberId}")
    public ResponseEntity<Member> getMemberWithId(@PathVariable Integer memberId) {
        try {
            return new ResponseEntity<>(memberService.getMemberWithId(memberId), HttpStatus.OK);
        } catch (Exception e) {
            log.error("/v1/member/{memberId}");
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        }
    }

    @GetMapping(path="/v1/member/nama")
    public ResponseEntity<Member> getMemberWithName(@RequestParam(value="nama") String nama) {
        try {
            return new ResponseEntity<>(memberService.getMemberWithName(nama), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(path="v1/member")
    public ResponseEntity<Member> addMember(@RequestBody Member addedMember) {
        try {
            return new ResponseEntity<>(memberService.addMember(addedMember), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update existing member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated member",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Member.class)) })})
    @PutMapping(path="v1/member")
    public ResponseEntity<Member> updateMember(@RequestBody Member updatedMember) {
        try {
            return new ResponseEntity<>(memberService.updateMember(updatedMember), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete member by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted member",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "member not found",
                    content = @Content) })
    @DeleteMapping(path="v1/member/{idMember}")
    public ResponseEntity<ResponseMessage> deleteMember(@Parameter(description = "id of book to be deleted") @PathVariable Integer idMember) {
        try {
            return new ResponseEntity<>(memberService.deleteMember(idMember), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
