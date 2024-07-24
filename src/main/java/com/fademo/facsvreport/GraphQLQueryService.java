package com.fademo.facsvreport;

import java.util.List;

import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GraphQLQueryService {
    private final FetchTokenService fetchTokenService;
    private final RestClient restClient;
    protected static final String FA_GRAPHQL_URL = "https://tryme.fasolutions.com/graphql";
    protected static final String QUERY_TEMPLATE = """
        query {
          transactions(portfolioId: %d%s) {
            portfolioShortName
            securityName
            security {
              isinCode
            }
            currencyCode
            amount
            unitPrice
            tradeAmount
            typeName
            transactionDate
            settlementDate
          }
        }
        """;

    public GraphQLQueryService(FetchTokenService fetchTokenService) {
        this.fetchTokenService = fetchTokenService;
        this.restClient = RestClient.create(FA_GRAPHQL_URL);
    }

    public List<Transaction> getTransactions(
            int portfolioId,
            @Nullable String startDate,
            @Nullable String endDate) {
        FaAccessToken token = fetchTokenService.getToken();
        HttpSyncGraphQlClient graphQlClient = HttpSyncGraphQlClient.builder(restClient)
                .header("Authorization", "bearer " + token.accessToken())
                .build();
        String dates = "";
        if (startDate != null) {
            dates += String.format(", startDate: \"%s\"", startDate);
        }
        if (endDate != null) {
            dates += String.format(", endDate: \"%s\"", endDate);
        }
        String query = String.format(QUERY_TEMPLATE, portfolioId, dates);
        return graphQlClient.document(query)
                .retrieveSync("transactions")
                .toEntityList(Transaction.class);
    }
}
