package model.solution

class Solution(val id: Int, val setName: String, val valuation: Seq[Boolean]){
  override def toString = s"Sol $setName($id) - (${valuation.map(v => if(v) 1 else 0).mkString(", ")})\n"
}
