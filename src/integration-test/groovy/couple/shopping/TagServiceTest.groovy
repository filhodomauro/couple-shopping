package couple.shopping

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * Created by maurofilho on 10/12/16.
 */
@Integration
@Rollback
class TagServiceTest extends Specification{

    @Autowired
    TagService tagService

    def "test that a tag is created"(){
        given:
        def tag = new Tag(description: "tag test created")

        when:
        def persistentTag = tagService.create tag

        then:
        persistentTag != null
        persistentTag.id != null
    }

    def "test that a tag id found by id"(){
        given:
        def tag = tagService.create new Tag(description: "tag test find")

        when:
        def foundTag = tagService.findOne tag.id

        then:
        foundTag != null
        foundTag.description == tag.description

    }

    def "test that a tag is created with lowercase"(){
        given:
        def tag = tagService.create new Tag(description: "TAG test LowerCase")

        when:
        def foundTag = tagService.findOne tag.id

        then:
        foundTag != null
        foundTag.description == tag.description.toLowerCase()
    }

}
