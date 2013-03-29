package com.elgoooog.nject

/**
 * @author Nicholas Hauschild
 * Date: 3/27/13
 * Time: 10:49 PM
 */
class Bean(_id:String, _clazz:Class[_], _constructorArgs:List[Class[_]], _properties:Map[String,Class[_]]) {
  val id = _id
  val clazz = _clazz
  val constructorArgs = _constructorArgs
  val properties = _properties
}
