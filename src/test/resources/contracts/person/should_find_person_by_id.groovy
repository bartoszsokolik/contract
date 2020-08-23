import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url '/api/persons/294c0951-b392-4fa9-9653-ad8f2b99b966'
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (
            id: '294c0951-b392-4fa9-9653-ad8f2b99b966',
                firstName: 'Jan',
                lastName: 'Kowalski'
        )
    }
}