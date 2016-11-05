package couple.shopping

import grails.plugins.rest.client.RestResponse
import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Stepwise

import static org.springframework.http.HttpStatus.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
@Stepwise
class UserSpec extends GebSpec {

    def "test that a user is created"() {
        setup:
        def user = [
                'username': 'filhodomauro',
                'name'    : 'Mauro',
                'password': '123456',
                'email'   : 'mauro.filho+couple@gmail.com'
        ]
        when: 'someone try to create a user'
        def resp = post '/users/create', user

        then: 'the user is created'
        resp.status == CREATED.value()
        resp.json.name == user.name
        resp.json.id != null
    }

    def "test that a login fail without confirmation"() {
        when: 'the user try a login without confirmation'
        def resp = post '/api/login', ['username': 'filhodomauro', 'password': '123456']

        then: 'the access is denied'
        resp.status == UNAUTHORIZED.value()
    }

    def "test that a confirmation is fail with invalid data"() {
        when: 'the confirmation is tried with invalid token'
        def resp = getRest "/users/confirm/filhodomauro/ffffrfrfrfffff"

        then: 'the user is not found'
        resp.status == NOT_FOUND.value()
    }

    def "test that a confirmation is successful"() {
        setup:
        def token = User.findByUsername('filhodomauro')?.confirmationToken
        when: 'the confirmation is tried with correct username and token'
        def resp = getRest "/users/confirm/filhodomauro/$token"

        then: 'the confirmation is successful'
        resp.status == OK.value()
    }

    def "test that login fail with invalid data"() {
        when: 'the user try a login after confirmation with invalid data'
        def resp = post '/api/login', ['username': 'filhodomauro', 'password': '000000']

        then: 'the access is denied'
        resp.status == UNAUTHORIZED.value()
    }

    def "test that login is successful"() {
        when: 'the user try a login after confirmation with valid data'
        def resp = post '/api/login', ['username': 'filhodomauro', 'password': '123456']

        then: 'the login is successful'
        resp.status == OK.value()
    }

    def "test that user data is updated"(){
        setup:
        def resp = post '/api/login', ['username': 'filhodomauro', 'password': '123456']
        def user = [
                'name': 'Mauro Filho',
                'email': 'mauro.filho+couple2@gmail.com'
        ]

        when: 'update is requested to valid and authenticated user'
        resp = restBuilder().put "$baseUrl/users/me", {
            header "content-type", "application/json"
            header "accept", "application/json"
            header "Authorization", "Bearer ${resp.json.access_token}"
            json(user)
        }

        then: 'user is updated'
        resp.status == OK.value()
        resp.json.name == user.name
        resp.json.email == user.email
    }

    RestResponse post(String path, def obj){
        restBuilder().post("${baseUrl}${path}",{
            header "content-type", "application/json"
            header "accept", "application/json"
            json(obj)
        })
    }

    RestResponse getRest(String path){
        restBuilder().get("${baseUrl}${path}",{
            header "content-type", "application/json"
            header "accept", "application/json"
        })
    }

    RestBuilder restBuilder() {
        new RestBuilder()
    }
}
