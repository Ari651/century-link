package com.centurylink

import org.barfuin.texttree.api.Node
import org.barfuin.texttree.api.TextTree
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class TreeHandlerSpec extends Specification {
    public static final String WINDOWS_LF = "\r\n"
    @SuppressWarnings("unused")
    public static final String LINUX_LF = "\n"
    @SuppressWarnings("unused")
    public static final String OLD_MAC_LF = "\r"


    //3rd party print library uses native encoding at runtime
    public static final String CR = WINDOWS_LF
//    public static final String CR = LINUX_LF
//    public static final String CR = OLD_MAC_LF

    @Subject
    TreeHandler underTest

    TextTree renderer

    void setup() {
        underTest = new TreeHandler()
        renderer = TextTree.newInstance()
    }

    @Unroll
    void "test buildTree"() {
        when: "We build a tree from the data"
        Node result = underTest.buildTree(data)


        then: "We get the expected hierarchy"
        renderer.render(result) == "0. grandpa$CR+--- 1. son$CR|    +--- 3. grandkid$CR|    `--- 4. grandkid$CR`--- 2. daugther$CR     `--- 5. grandkid$CR          `--- 6. greatgrandkid$CR"


        where: "The records can be in any order"
        data |_
        "null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid" |_
        "0,1,son|null,0,grandpa|5,6,greatgrandkid|0,2,daugther|1,4,grandkid|1,3,grandkid|2,5,grandkid" |_
        "5,6,greatgrandkid|0,2,daugther|null,0,grandpa|0,1,son|1,4,grandkid|2,5,grandkid|1,3,grandkid" |_
    }
}
