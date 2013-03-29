package com.elgoooog.nject

import example.Animal
import org.scalatest.FunSuite
import collection.mutable.HashMap

/**
 * @author Nicholas Hauschild
 * Date: 3/27/13
 * Time: 11:14 PM
 */
class BeanTest extends FunSuite {
  test("Simple bean creation") {
    val bean = new Bean("iii", classOf[String], List.empty, Map.empty)

    assert("iii" == bean.id)
    assert(classOf[String] == bean.clazz)
    assert(bean.constructorArgs.isEmpty)
    assert(bean.properties.isEmpty)
  }

  test("Less simple bean creation") {
    val props = new HashMap[String,Class[_]]
    props.put("name", classOf[String])
    props.put("species", classOf[String])
    val bean = new Bean("bbb", classOf[Animal], List.empty, props.toMap)

    assert("bbb" == bean.id)
    assert(classOf[Animal] == bean.clazz)
    assert(bean.constructorArgs.isEmpty)
    assert(!bean.properties.isEmpty)
    assert(2 == bean.properties.size)
  }
}
