package co.com.pragma.api;

import co.com.pragma.api.dto.CreateLoanDTO;
import co.com.pragma.api.exception.GlobalExceptionFilter;
import co.com.pragma.model.loan.Loan;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {


    @RouterOperation(
            operation = @Operation(
                    operationId = "loans", summary = "Save a user", tags = { "Loans" },
                    requestBody = @RequestBody(
                            required = true,
                            description = "Create a Loan",
                            content = @Content(
                                    schema = @Schema(implementation = CreateLoanDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Loan example",
                                                    value = """
                                                        {
                                                            "amount": 5000,
                                                            "term": 5,
                                                            "dniUser": "123456789",
                                                            "idLoanType": 1
                                                        }
                                                        """
                                            )
                                    }
                            )
                    ),
                    responses = {
                            @ApiResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = Loan.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid data"),
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler, GlobalExceptionFilter filter) {
        return route(POST("/api/v1/requests"), handler::createOne)
                .filter(filter);
    }

    @Bean
    public RouterFunction<ServerResponse> getLoanFunction(Handler handler, GlobalExceptionFilter filter){
        return route(GET("/api/v1/requests"), handler::findAll)
                .filter(filter);
    }
}
