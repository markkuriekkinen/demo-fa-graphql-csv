package com.fademo.facsvreport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.FieldAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    private GraphQLQueryService graphQLQueryService;

    @GetMapping("/portfolios/{portfolioId}/transactions")
    public ResponseEntity<List<Transaction>> transactions(
            @PathVariable(name = "portfolioId") int portfolioId,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate) {
        List<Transaction> result = graphQLQueryService.getTransactions(portfolioId, startDate, endDate);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv")).body(result);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN,
                    reason = "Not allowed to view the requested portfolio.")
    @ExceptionHandler(FieldAccessException.class)
    public void permissionDenied() {
    }
}
