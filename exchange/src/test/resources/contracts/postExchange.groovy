package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should exchange sum"

    request {
        url "/exchange"
        method POST()
        headers {
            contentType applicationJson()
        }
        body '''
                {
                    "fromCurrency": "RUB",
                    "toCurrency": "USD",
                    "amount": 100.0
                }
        '''
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                1.0
        )
    }

}