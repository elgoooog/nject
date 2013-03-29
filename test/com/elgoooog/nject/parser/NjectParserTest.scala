package com.elgoooog.nject.parser

import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * @author Nicholas Hauschild
 * Date: 3/26/13
 * Time: 11:59 PM
 */
class NjectParserTest extends FunSuite with BeforeAndAfter {
  var parser:NjectParser = _

  before {
    parser = new NjectParser()
  }

  test("nject parser") {
    val beans = parser.parse("test/example.xml")

    for(bean <- beans.beans) {
      println(bean.id)
    }
  }
}
