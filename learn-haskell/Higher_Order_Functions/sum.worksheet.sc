

def sum(l: List[Int]) = l.foldLeft(0)(_)


sum(List(1,2,3))( (a: Int, b: Int) => a+b)