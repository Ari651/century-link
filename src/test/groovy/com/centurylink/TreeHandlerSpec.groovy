package com.centurylink

import spock.lang.Specification
import spock.lang.Subject

class TreeHandlerSpec extends Specification {

    @Subject
    TreeHandler underTest
    void setup() {
        underTest = new TreeHandler()
    }

    void "test buildTree"() {
        given:
        true

        when:
        underTest.buildTree(data)
        then:
        false

        where:
        data |_
        'null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid' |_
    }
}
