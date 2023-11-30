package ras

import ras.ui.BuildUI

object Main {
  def main(args: Array[String]): Unit = {
    val ui = new BuildUI()
    ui.setupUI()
  }
}
