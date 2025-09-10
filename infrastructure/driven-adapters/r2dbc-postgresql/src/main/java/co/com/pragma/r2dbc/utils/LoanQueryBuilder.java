package co.com.pragma.r2dbc.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoanQueryBuilder {

    public String findAllInfo(Map<String, Object> params){
        StringBuilder sql = new StringBuilder("""
                SELECT l.id, l.amount, l.term, l.dni_user, lt.id as id_loan_type, lt.name, lt.interest_rate, s.id as id_state, s.name as state_name
                FROM loans l
                INNER JOIN loan_types lt ON l.id_loan_type = lt.id
                INNER JOIN states s ON l.id_state = s.id
                WHERE 1=1
        """);

        if(params.containsKey("state")){
            sql.append(" AND s.id = :state");
        }

        sql.append(" ORDER BY l.id LIMIT :limit OFFSET :offset");

        return sql.toString();
    }
}
