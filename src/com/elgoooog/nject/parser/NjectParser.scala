package com.elgoooog.nject.parser

import io.Source
import java.io.File
import xml.pull.{EvElemEnd, EvElemStart, XMLEventReader}
import com.elgoooog.nject._
import compat.Platform
import xml.MetaData
import collection.mutable
import xml.pull.EvElemStart
import xml.pull.EvElemEnd

/**
 * @author Nicholas Hauschild
 * Date: 3/26/13
 * Time: 11:57 PM
 */
class NjectParser {
  def parse(file : String) : BeanContainer = {
    parse(new File(file))
  }

  def parse(file : File) : BeanContainer = {
    parse(Source.fromFile(file))
  }

  def parse(file : Source) : BeanContainer = {
    val reader = new XMLEventReader(file)
    val beans = new scala.collection.mutable.HashSet[Bean]

    verifyRootElement(reader)
    var beanBuilder : BeanBuilder = null
    var props : mutable.Map[String, DataHolder] = null
    var constructorArgs : mutable.MutableList[DataHolder] = null

    while(reader.hasNext) {
      val event = reader.next()
      event match {
        case EvElemStart(_,"bean",attrs,_) => {
          beanBuilder = new BeanBuilder
          val className = attrs.head("class").text
          val id = attrs.head("id").text
          val clazz = Platform.getClassForName(className)

          props = new mutable.HashMap[String, DataHolder]
          constructorArgs = new mutable.MutableList[DataHolder]

          beanBuilder.withId(id).withClass(clazz)
        }
        case EvElemStart(_,"property",attrs,_) => {
          val dataHolder = validateElement(attrs)
          val propName = validatePropName(attrs)
          props.put(propName, dataHolder)
        }
        case EvElemStart(_,"constructor-arg",attrs,_) => {
          val dataHolder = validateElement(attrs)
          constructorArgs += dataHolder
        }
        case EvElemEnd(_,"bean") => {
          beanBuilder.withConstructorArgs(constructorArgs.toList)
          beanBuilder.withProperties(props.toMap)
          beans.add(beanBuilder.build())
        }
        case _ => {}
      }
    }

    new BeanContainer(beans.toSet)
  }

  def validateElement(data : MetaData) : DataHolder = {
    val value = data.head("value")
    val ref = data.head("ref")
    if (value != null && value.text != null) {
      assert(ref == null)
      new ValueHolder(value.text)
    }
    else if (ref != null && ref.text != null) {
      assert(value == null)
      new RefHolder(ref.text)
    }
    else {
      throw new IllegalArgumentException("ref/value conflict")
    }
  }

  def validatePropName(data : MetaData) : String = {
    val name = data.head("name")
    if (name != null && name.text != null) {
      name.text
    }
    else {
      throw new IllegalArgumentException("prop name missing")
    }
  }

  private def verifyRootElement(reader : XMLEventReader) {
    assert(reader.hasNext)
    val event = reader.next()
    event match {
      case EvElemStart(_,elementName,_,_) => {
        assert("nject" == elementName)
      }
    }
  }
}
