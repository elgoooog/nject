package com.elgoooog.nject

/**
 * @author Nicholas Hauschild
 * Date: 3/29/13
 * Time: 8:44 PM
 */
class RefHolder(_ref:String) extends DataHolder {
  val ref = _ref

  override def toString = "RefHolder[ref=" + ref + "]"
}
