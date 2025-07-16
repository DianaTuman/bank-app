package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return rates"

    request {
        url "/exchange/rates"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body '''
                    {"currencyDTOS":
                    [
                    {"title":"Russian Ruble","name":"RUB","value":1.0},
                    {"title":"United States Dollar","name":"USD","value":0.01}
                    ]
                    }
            '''
    }

}