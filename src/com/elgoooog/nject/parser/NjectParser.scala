package com.elgoooog.nject.parser

import io.Source
import java.io.File
import xml.pull.{EvElemEnd, EvElemStart, XMLEventReader}
import com.elgoooog.nject.{BeanBuilder, Bean, BeanContainer}
import compat.Platform
import xml.MetaData
import collection.mutable

/**
 * @author Nicholas Hauschild
 * Date: 3/26/13
 * Time: 11:57 PM
 */
class NjectParser {
  def parse(file:String):BeanContainer = {
    parse(new File(file))
  }

  def parse(file:File):BeanContainer = {
    parse(Source.fromFile(file))
  }

  def parse(file:Source):BeanContainer = {
    val reader = new XMLEventReader(file)
    val beans = new scala.collection.mutable.HashSet[Bean]

    verifyRootElement(reader)
    var beanBuilder:BeanBuilder = null
    var props:mutable.Map[String, Class[_]] = null
    var constructorArgs:mutable.MutableList[Class[_]] = null

    while(reader.hasNext) {
      val event = reader.next()
      event match {
        case EvElemStart(_,"bean",attrs,_) => {
          beanBuilder = new BeanBuilder
          val className = attrs.head("class").text
          val id = attrs.head("id").text
          val clazz = Platform.getClassForName(className)

          props = new mutable.HashMap[String, Class[_]]
          constructorArgs = new mutable.MutableList[Class[_]]

          beanBuilder.withId(id).withClass(clazz)
        }
        case EvElemStart(_,"property",attrs,_) => {
          validateElement(attrs)
        }
        case EvElemStart(_,"constructor-arg",attrs,_) => {
          validateElement(attrs)
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

  def validateElement(data: MetaData) {
    val value = data.head("value")
    val ref = data.head("ref")
    assert((value != null && ref == null) || (value == null && ref != null))
  }

  private def verifyRootElement(reader:XMLEventReader) {
    assert(reader.hasNext)
    val event = reader.next()
    event match {
      case EvElemStart(_,elementName,_,_) => {
        assert("nject" == elementName)
      }
    }
  }
}
