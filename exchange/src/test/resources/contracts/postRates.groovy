package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should set rates"

    request {
        url "/exchange/rates"
        method POST()
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

    response {
        status OK()
    }

}