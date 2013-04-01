package com.elgoooog.nject

/**
 * @author Nicholas Hauschild
 * Date: 3/28/13
 * Time: 10:55 PM
 */
class BeanBuilder {
  var id:String = _
  var clazz:Class[_] = _
  var constructorArgs:List[DataHolder] = _
  var properties:Map[String,DataHolder] = _

  def withId(_id:String):BeanBuilder = {
    id = _id
    this
  }

  def build():Bean = {
    new Bean(id, clazz, constructorArgs, properties)
  }

  def withClass(_clazz:Class[_]):BeanBuilder = {
    clazz = _clazz
    this
  }

  def withConstructorArgs(_constructorArgs:List[DataHolder]):BeanBuilder = {
    constructorArgs = _constructorArgs
    this
  }

  def withProperties(_properties:Map[String,DataHolder]):BeanBuilder = {
    properties = _properties
    this
  }
}
