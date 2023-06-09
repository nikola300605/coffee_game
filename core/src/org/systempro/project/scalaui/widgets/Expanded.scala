package org.systempro.project.scalaui.widgets

import org.systempro.project.scalaui.{SingleChildWidget, Size, Widget}

class Expanded(child:Widget=null,var flex:Float=1) extends SingleChildWidget(child:Widget) {

  override def calculateSize(maxSize: Size): Size = {
    super.calculateSize(maxSize);
    size.set(maxSize);
  }
}
