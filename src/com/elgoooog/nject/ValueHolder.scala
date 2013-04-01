package com.elgoooog.nject

/**
 * @author Nicholas Hauschild
 * Date: 3/29/13
 * Time: 8:43 PM
 */
class ValueHolder(_value:String) extends DataHolder {
  val value = _value

  override def toString = "ValueHolder[value=" + value + "]"
}
