require "./hw7.rb"

class Point
  def == other
    (other.is_a? Point) && real_close(x,other.x) && real_close(y,other.y)
  end
end

class NoPoints
  def == other
    other.is_a? NoPoints
  end
end

class Line
  def == other
    (other.is_a? Line) && other.m==m && other.b==b
  end
end  


ZERO = 0.0
ONE = 1.0
TWO = 2.0
THREE = 3.0
FOUR = 4.0
FIVE = 5.0
SIX = 6.0
SEVEN = 7.0
TEN = 10.0

i1 = Intersect.new(LineSegment.new(-ONE,-TWO,THREE,FOUR), LineSegment.new(THREE,FOUR,-ONE,-TWO))
i1_eval = i1.preprocess_prog.eval_prog([])
puts (i1_eval.is_a? LineSegment) && i1_eval.x1==-ONE && i1_eval.y1==-TWO && i1_eval.x2==THREE && i1_eval.y2==FOUR

seg = LineSegment.new(-TWO, -TWO, TWO, TWO)
p1 = Point.new(FIVE, FIVE)
i2 = Intersect.new(seg, p1)
i2_eval = i2.preprocess_prog.eval_prog([])
puts i2_eval.is_a? NoPoints

vx = Var.new("x")
i3 = vx.eval_prog ([["x", NoPoints.new], ["x", Point.new(0.0,0.0)]])
puts i3.is_a? NoPoints

i4 = vx.eval_prog ([["x", NoPoints.new], ["y", Point.new(0.0,0.0)], ["x", Point.new(3.0,2.0)]])
puts i4.is_a? NoPoints


############ special set of complete intersect tests follows ####################
np = NoPoints.new
p1_x = 11.0/3.0
p1_y = p1_x-1
p1 = Point.new p1_x, p1_y
p2 = Point.new TWO, ONE
p3 = Point.new THREE, FIVE
pvl1 = Point.new FIVE, FOUR
pvl2 = Point.new FIVE, TWO
l1 = Line.new ONE, -ONE
l2 = Line.new -0.5, 4.5
l3 = VerticalLine.new FIVE
l4 = Line.new ONE, FIVE
l5_setup = Shift.new -SIX, ZERO, l3
l5 = l5_setup.preprocess_prog.eval_prog([])
seg1 = LineSegment.new TWO, ONE, FIVE, FOUR
seg2 = LineSegment.new THREE, FIVE, FIVE, FOUR
seg3 = LineSegment.new TWO, ONE, p1_x, p1_y

#puts p1, p2, p3, pvl1, pvl2, l1, l2, l3, l4, l5, seg1, seg2, seg3

#####################################
### POINT
## POINT
pp_ok = (Intersect.new p2, p2).preprocess_prog.eval_prog([])
puts "PP_OK: " + (pp_ok==p2).to_s
pp_err = (Intersect.new p2, p1).preprocess_prog.eval_prog([])
puts "PP_ERR: " + (pp_err==np).to_s
## LINE
pl_ok = (Intersect.new p1, l1).preprocess_prog.eval_prog([])
puts "PL_OK: " + (pl_ok==p1).to_s
pl_err = (Intersect.new p3, l1).preprocess_prog.eval_prog([])
puts "PL_ERR: " + (pl_err==np).to_s
## VERTICAL LINE
pvl_ok = (Intersect.new pvl1, l3).preprocess_prog.eval_prog([])
puts "PVL_OK: " + (pvl_ok==pvl1).to_s
pvl_err = (Intersect.new p2, l3).preprocess_prog.eval_prog([])
puts "PVL_ERR: " + (pvl_err==np).to_s
## LINE SEGMENT
pls_ok = (Intersect.new p3, seg2).preprocess_prog.eval_prog([])
puts "PLS_OK: " + (pls_ok==p3).to_s
pls_err = (Intersect.new p3, seg1).preprocess_prog.eval_prog([])
puts "PLS_ERR: " + (pls_err==np).to_s

#####################################
### LINE
## POINT
lp_ok = (Intersect.new l1,p1).preprocess_prog.eval_prog([])
puts "LP_OK: " + (lp_ok==p1).to_s
lp_err = (Intersect.new l1,p3).preprocess_prog.eval_prog([])
puts "LP_ERR: " + (lp_err==np).to_s
## LINE
lla_ok = (Intersect.new l1, l2).preprocess_prog.eval_prog([])
llb_ok = (Intersect.new l2, l1).preprocess_prog.eval_prog([])
puts "LLA_OK: " + (lla_ok==llb_ok && lla_ok==p1).to_s
llc_ok = (Intersect.new l1, l1).preprocess_prog.eval_prog([])
puts "LLC_OK: " + (llc_ok==l1).to_s
lla_err = (Intersect.new l1, l4).preprocess_prog.eval_prog([])
puts "LLA_ERR: " + (np==lla_err).to_s
## VERTICAL LINE
lvl_ok = (Intersect.new l2,l3).preprocess_prog.eval_prog([])
puts "LVL_OK: " + (lvl_ok==pvl2).to_s
## LINE SEGMENT
lls_ok = (Intersect.new l1, seg2).preprocess_prog.eval_prog([])
puts "LLS_OK: " + (lls_ok==pvl1).to_s
lls_err = (Intersect.new l2, seg2).preprocess_prog.eval_prog([])
puts "LLS_ERR: " + (lls_err==np).to_s


#####################################
### VERTICAL LINE
## POINT
vlp_ok = (Intersect.new l3, pvl1).preprocess_prog.eval_prog([])
puts "VLP_OK: " + (vlp_ok==pvl1).to_s
vlp_err = (Intersect.new l3, p2).preprocess_prog.eval_prog([])
puts "VLP_ERR: " + (vlp_err==np).to_s
## LINE
vll_ok = (Intersect.new l3,l2).preprocess_prog.eval_prog([])
puts "VLL_OK: " + (vll_ok==pvl2).to_s
## VERTICAL LINE
vlvl_ok = (Intersect.new l3, l3).preprocess_prog.eval_prog([])
puts "VLVL_OK: " + (vlvl_ok==l3).to_s
vlvl_err = (Intersect.new l3, l5).preprocess_prog.eval_prog([])
puts "VLVL_ERR: " + (vlvl_err==np).to_s
## LINE SEGMENT
vlls_ok = (Intersect.new l3, seg1).preprocess_prog.eval_prog([])
puts "VLLS_OK: " + (vlls_ok==pvl1).to_s
vlls_err = (Intersect.new l4, seg3).preprocess_prog.eval_prog([])
puts "VLLS_ERR: " + (vlls_err==np).to_s

#####################################
### LINE SEGMENT
## POINT
lsp_ok = (Intersect.new seg2,p3).preprocess_prog.eval_prog([])
puts "LSP_OK: " + (lsp_ok==p3).to_s
lsp_err = (Intersect.new seg1,p3).preprocess_prog.eval_prog([])
puts "LSP_ERR: " + (lsp_err==np).to_s
## LINE
lsl_ok = (Intersect.new seg2,l1).preprocess_prog.eval_prog([])
puts "LSL_OK: " + (lsl_ok==pvl1).to_s
lsl_err = (Intersect.new seg2,l2).preprocess_prog.eval_prog([])
puts "LSL_ERR: " + (lsl_err==np).to_s
## VERTICAL LINE
lsvl_ok = (Intersect.new seg1,l3).preprocess_prog.eval_prog([])
puts "VLLS_OK: " + (lsvl_ok==pvl1).to_s
lsvl_err = (Intersect.new seg3,l4).preprocess_prog.eval_prog([])
puts "VLLS_ERR: " + (lsvl_err==np).to_s
## LINE SEGMENT
lsls_ok = (Intersect.new seg1,seg2).preprocess_prog.eval_prog([])
puts "LSLS_OK: " + (lsls_ok==pvl1).to_s
lsls_err = (Intersect.new seg3,seg2).preprocess_prog.eval_prog([])
puts "LSLS_ERR: " + (lsls_err==np).to_s

#################################
# other tests

class Intersect
  attr_reader :e1, :e2
end
class Let
  attr_reader :s, :e1, :e2
end
class Var
  attr_reader :s
end
class Shift
  attr_reader :dx, :dy, :e
end
def real_close(r1,r2) 
  (r1 - r2).abs < GeometryExpression::Epsilon
end
def equal(e1, e2)
  if e1.class.name != e2.class.name
    return false
  end

  if e1.is_a? NoPoints
    true
  elsif e1.is_a? Point
    # == should be real_close instead.
    #e1.x == e2.x and e1.y == e2.y
    real_close(e1.x,e2.x) && real_close(e1.y,e2.y)
  elsif e1.is_a? Line
    # == should be real_close instead.
    e1.m == e2.m and e1.b == e2.b
  elsif e1.is_a? VerticalLine
    # == should be real_close instead.
    e1.x == e2.x
  elsif e1.is_a? LineSegment
    # == should be real_close instead.
    e1.x1 == e2.x1 and e1.y1 == e2.y1 and e1.x2 == e2.x2 and e1.y2 == e2.y2
  elsif e1.is_a? Intersect
    equal(e1.e1, e2.e1) and equal(e1.e2, e2.e2)
  elsif e1.is_a? Let
    e1.s == e2.s and equal(e1.e1, e2.e1) and equal(e1.e2, e2.e2)
  elsif e1.is_a? Var
    e1.s == e2.s
  elsif e1.is_a? Shift
    # == should be real_close instead.
    e1.dx == e2.dx and e1.dy == e2.dy and equal(e1.e, e2.e)
  else
    false
  end
end

tests = []
tests[0] = equal(NoPoints.new.preprocess_prog, NoPoints.new)
tests[1] = equal(Point.new(1.0, 1.0).preprocess_prog, Point.new(1.0, 1.0))
tests[2] = equal(Line.new(1.0, 2.0).preprocess_prog, Line.new(1.0, 2.0))
tests[3] = equal(VerticalLine.new(2.0).preprocess_prog, VerticalLine.new(2.0))
tests[4] = equal(LineSegment.new(1.0, 1.0, 1.0, 1.0).preprocess_prog, Point.new(1.0, 1.0))
tests[5] = equal(LineSegment.new(1.0, 1.0, 1.0, 2.0).preprocess_prog, LineSegment.new(1.0, 1.0, 1.0, 2.0))
tests[6] = equal(LineSegment.new(1.0, 1.0, 1.0, 0.0).preprocess_prog, LineSegment.new(1.0, 0.0, 1.0, 1.0))
tests[7] = equal(LineSegment.new(1.0, 1.0, 2.0, 1.0).preprocess_prog, LineSegment.new(1.0, 1.0, 2.0, 1.0))
tests[8] = equal(LineSegment.new(1.0, 0.0, 2.0, 1.0).preprocess_prog, LineSegment.new(1.0, 0.0, 2.0, 1.0))
tests[9] = equal(LineSegment.new(1.0, 1.0, 2.0, 0.0).preprocess_prog, LineSegment.new(1.0, 1.0, 2.0, 0.0))
tests[10] = equal(LineSegment.new(2.0, 1.0, 1.0, 1.0).preprocess_prog, LineSegment.new(1.0, 1.0, 2.0, 1.0))
tests[11] = equal(LineSegment.new(2.0, 0.0, 1.0, 1.0).preprocess_prog, LineSegment.new(1.0, 1.0, 2.0, 0.0))
tests[12] = equal(LineSegment.new(2.0, 1.0, 1.0, 0.0).preprocess_prog, LineSegment.new(1.0, 0.0, 2.0, 1.0))

tests[13] = equal(LineSegment.new(1.00000999, 1.0, 1.0, 2.0).preprocess_prog, LineSegment.new(1.00000999, 1.0, 1.0, 2.0)) 

tests[14] = equal(Let.new("x", LineSegment.new(1.0, 1.0, 1.0, 1.0), Var.new("x")).preprocess_prog, Let.new("x", Point.new(1.0, 1.0), Var.new("x")))
tests[15] = equal(Shift.new(1.0, 1.0, LineSegment.new(1.0, 1.0, 1.0, 1.0)).preprocess_prog, Shift.new(1.0, 1.0, Point.new(1.0, 1.0)))
tests[16] = equal(Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 1.0), LineSegment.new(2.0, 1.0, 1.0, 0.0)).preprocess_prog, Intersect.new(Point.new(1.0, 1.0), LineSegment.new(1.0, 0.0, 2.0, 1.0)))
tests[17] = equal(Shift.new(1.0, 1.0, Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 1.0), LineSegment.new(2.0, 1.0, 1.0, 0.0))).preprocess_prog, Shift.new(1.0, 1.0, Intersect.new(Point.new(1.0, 1.0), LineSegment.new(1.0, 0.0, 2.0, 1.0))))

# eval_prog
# Shift
tests[20] = equal(Shift.new(1.0, 1.0, Point.new(2.0, 3.0)).eval_prog([]), Point.new(3.0, 4.0))
tests[21] = equal(Shift.new(2.0, 10.3, Line.new(3.0, 5.0)).eval_prog([]), Line.new(3.0, 9.3))
tests[22] = equal(Shift.new(1.0, 2.0, VerticalLine.new(5.0)).eval_prog([]), VerticalLine.new(6.0))
tests[23] = equal(Shift.new(2.0, 2.0, LineSegment.new(1.0, 1.0, 1.0, 1.0)).preprocess_prog.eval_prog([]), Point.new(3.0, 3.0))
tests[24] = equal(Shift.new(1.0, 2.0, LineSegment.new(1.0, 1.0, 3.0, 3.0)).preprocess_prog.eval_prog([]), LineSegment.new(2.0, 3.0, 4.0, 5.0)) 
tests[25] = equal(Shift.new(1.0, 1.0, Shift.new(1.0, 1.0, Shift.new(1.0, 1.0, Point.new(1.0, 1.0)))).preprocess_prog.eval_prog([]), Point.new(4.0, 4.0))


# Let
tests[31] = equal(Let.new("x", Point.new(2.0, 3.0), Var.new("x")).preprocess_prog.eval_prog([]), Point.new(2.0, 3.0)) 
tests[32] = equal(Let.new("x", Line.new(3.0, 5.0), Var.new("x")).preprocess_prog.eval_prog([]), Line.new(3.0, 5.0))
tests[33] = equal(Let.new("x", VerticalLine.new(1.0), Var.new("x")).preprocess_prog.eval_prog([]), VerticalLine.new(1.0))
tests[34] = equal(Let.new("x", LineSegment.new(2.0, 2.0, 1.0, 1.0), Var.new("x")).preprocess_prog.eval_prog([]), LineSegment.new(1.0, 1.0, 2.0, 2.0))
tests[35] = equal(Let.new("x", Point.new(1.0, 1.0), Let.new("x", Point.new(2.0, 2.0), Var.new("x"))).preprocess_prog.eval_prog([]), Point.new(2.0, 2.0))

# Let + Shift
tests[40] = equal(Let.new("x", LineSegment.new(1.0, 1.0, 1.0, 1.0), Shift.new(1.0, 1.0, Var.new("x"))).preprocess_prog.eval_prog([]), Point.new(2.0, 2.0))
tests[41] = equal(Shift.new(1.0, 1.0, Let.new("x", VerticalLine.new(2.0), Var.new("x"))).preprocess_prog.eval_prog([]), VerticalLine.new(3.0))

# Intersect
# NoPoints
tests[45] = equal(Intersect.new(NoPoints.new, NoPoints.new).preprocess_prog.eval_prog([]), NoPoints.new)
tests[46] = equal(Intersect.new(NoPoints.new, Point.new(1.0, 2.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[47] = equal(Intersect.new(NoPoints.new, Line.new(1.0, 1.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[48] = equal(Intersect.new(NoPoints.new, VerticalLine.new(1.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[49] = equal(Intersect.new(NoPoints.new, LineSegment.new(1.0, 1.0, 3.0, 3.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[50] = equal(Intersect.new(Point.new(1.0, 1.0), Point.new(1.00000999, 1.0)).preprocess_prog.eval_prog([]), Point.new(1.00000999, 1.0))
tests[51] = equal(Intersect.new(Point.new(1.0, 1.0), Point.new(1.0999, 1.0)).preprocess_prog.eval_prog([]), NoPoints.new)
# Point
tests[52] = equal(Intersect.new(Point.new(1.0, 4.0), Line.new(1.0, 3.0)).preprocess_prog.eval_prog([]), Point.new(1.0, 4.0))
tests[53] = equal(Intersect.new(Point.new(2.0, 4.0), Line.new(1.0, 3.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[54] = equal(Intersect.new(Point.new(2.0, 4.0), VerticalLine.new(2.0)).preprocess_prog.eval_prog([]), Point.new(2.0, 4.0))
tests[55] = equal(Intersect.new(Point.new(4.0, 4.0), VerticalLine.new(2.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[56] = equal(Intersect.new(Point.new(2.0, 2.0), LineSegment.new(0.0, 0.0, 3.0, 3.0)).preprocess_prog.eval_prog([]), Point.new(2.0, 2.0))
tests[57] = equal(Intersect.new(Point.new(4.0, 4.0), LineSegment.new(0.0, 0.0, 3.0, 3.0)).preprocess_prog.eval_prog([]), NoPoints.new)
# Line
tests[58] = equal(Intersect.new(Line.new(1.0, 2.0), Line.new(2.0, 0.0)).preprocess_prog.eval_prog([]), Point.new(2.0, 4.0))
tests[59] = equal(Intersect.new(Line.new(1.0, 2.0), Line.new(1.0, 3.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[60] = equal(Intersect.new(Line.new(1.0, 2.0), VerticalLine.new(2.0)).preprocess_prog.eval_prog([]), Point.new(2.0, 4.0))
tests[61] = equal(Intersect.new(Line.new(1.0, 0.0), LineSegment.new(0.0, 0.0, 4.0, 4.0)).preprocess_prog.eval_prog([]), LineSegment.new(0.0, 0.0, 4.0, 4.0))
tests[62] = equal(Intersect.new(Line.new(2.0, 0.0), LineSegment.new(0.0, 0.0, 4.0, 4.0)).preprocess_prog.eval_prog([]), Point.new(0.0, 0.0))
tests[63] = equal(Intersect.new(Line.new(2.0, 0.0), LineSegment.new(1.0, 1.0, 4.0, 4.0)).preprocess_prog.eval_prog([]), NoPoints.new)

# VerticalLine
tests[64] = equal(Intersect.new(VerticalLine.new(1.0), VerticalLine.new(2.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[65] = equal(Intersect.new(VerticalLine.new(1.0), LineSegment.new(1.0, 0.0, 1.0, 4.0)).preprocess_prog.eval_prog([]), LineSegment.new(1.0, 0.0, 1.0, 4.0))
tests[66] = equal(Intersect.new(VerticalLine.new(1.0), LineSegment.new(0.0, 1.0, 3.0, 1.0)).preprocess_prog.eval_prog([]), Point.new(1.0, 1.0))
tests[67] = equal(Intersect.new(VerticalLine.new(1.0), LineSegment.new(2.0, 2.0, 4.0, 4.0)).preprocess_prog.eval_prog([]), NoPoints.new)

# LineSegment
tests[70] = equal(Intersect.new(LineSegment.new(0.0, 0.0, 0.0, 4.0), LineSegment.new(1.0, 0.0, 1.0, 4.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[71] = equal(Intersect.new(LineSegment.new(0.0, 0.0, 4.0, 0.0), LineSegment.new(0.0, 1.0, 4.0, 1.0)).preprocess_prog.eval_prog([]), NoPoints.new)
tests[72] = equal(Intersect.new(LineSegment.new(2.0, 0.0, 2.0, 4.0), LineSegment.new(0.0, 2.0, 4.0, 2.0)).preprocess_prog.eval_prog([]), Point.new(2.0, 2.0))
tests[73] = equal(Intersect.new(LineSegment.new(0.0, 0.0, 0.0, 4.0), LineSegment.new(0.0, 1.0, 0.0, 3.0)).preprocess_prog.eval_prog([]), LineSegment.new(0.0, 1.0, 0.0, 3.0))
tests[74] = equal(Intersect.new(LineSegment.new(0.0, 1.0, 0.0, 3.0), LineSegment.new(0.0, 0.0, 0.0, 4.0)).preprocess_prog.eval_prog([]), LineSegment.new(0.0, 1.0, 0.0, 3.0))
tests[75] = equal(Intersect.new(LineSegment.new(0.0, 1.0, 0.0, 3.0), LineSegment.new(0.0, 1.0, 0.0, 3.0)).preprocess_prog.eval_prog([]), LineSegment.new(0.0, 1.0, 0.0, 3.0))
tests[76] = equal(Intersect.new(LineSegment.new(0.0, 0.0, 0.0, 3.0), LineSegment.new(0.0, 1.0, 0.0, 4.0)).preprocess_prog.eval_prog([]), LineSegment.new(0.0, 1.0, 0.0, 3.0))


tests.each_with_index {|v,i| puts "#{i}: #{v}"}


#########################
# and more tests

class Point < GeometryValue
  def ==(p)
    self.class == p.class and @x == p.x and @y == p.y
  end
end

class Line < GeometryValue
  def ==(l)
    self.class == l.class and @m == l.m and @b == l.b
  end
end

class VerticalLine < GeometryValue
  def ==(vl)
    self.class == vl.class and @x == vl.x
  end
end

class LineSegment < GeometryValue
  def ==(ls)
    self.class == ls.class and @x1 == ls.x1 and @y1 == ls.y1 and @x2 == ls.x2 and @y2 == ls.y2
  end
end

class Intersect < GeometryExpression
  def ==(i)
    self.class == i.class and @e1 == i.e1 and @e2 == i.e2
  end
end

class Let < GeometryExpression
  def ==(l)
    self.class == l.class and @s == l.s and @e1 == l.e1 and @e2 == l.e2
  end
end

class Var < GeometryExpression
  def ==(v)
    self.class == v.class and @s == v.s
  end
end

class Shift < GeometryExpression
  def ==(s)
    self.class == s.class and @dx == s.dx and @dy == s.dy and @e == s.e
  end
end

=begin
if not (Let.new("a", LineSegment.new(3.2,4.1,3.2,4.1), LineSegment.new(3.2,4.1,3.2,4.1)).preprocess_prog == Let.new("a", Point.new(3.2, 4.1), Point.new(3.2, 4.1)))
  puts "Let preprocess_prog test fails"
end

if not (Shift.new(1.0, 2.0, LineSegment.new(3.2,4.1,3.2,4.1)).preprocess_prog == Shift.new(1.0, 2.0, Point.new(3.2, 4.1)))
  puts "Shift preprocess_prog test fails"
end

if not (Intersect.new(LineSegment.new(3.2,4.1,3.2,4.1), LineSegment.new(3.2,4.1,3.2,4.1)).preprocess_prog == Intersect.new(Point.new(3.2, 4.1), Point.new(3.2, 4.1)))
  puts "Intersect preprocess_prog test fails"
end

if not (Shift.new(3.0, 4.0, Point.new(4.0, 4.0)).eval_prog([]) == Point.new(7.0, 8.0))
  puts "Shift eval_prog test fails with Point"
end

if not (Shift.new(3.0, 4.0, Line.new(4.0, 4.0)).eval_prog([]) == Line.new(4.0, -4.0))
  puts "Shift eval_prog test fails with Line"
end

if not (Shift.new(3.0, 4.0, VerticalLine.new(4.0)).eval_prog([]) == VerticalLine.new(7.0))
  puts "Shift eval_prog test fails with VerticalLine"
end

if not (Shift.new(3.0, 4.0, LineSegment.new(4.0, 3.0, 12.0, -2.0)).eval_prog([]) == LineSegment.new(7.0, 7.0, 15.0, 2.0))
  puts "Shift eval_prog test fails with LineSegment"
end

if not (Intersect.new(Point.new(4.0, 4.0), Point.new(4.0, 4.0)).eval_prog([]) == Point.new(4.0, 4.0))
  puts "Intersect test #1 fails for Point/Point"
end
if not (Intersect.new(Point.new(4.0, 4.0), Point.new(4.0, 4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #2 fails for Point/Point"
end
if not (Intersect.new(Point.new(4.0, 4.0), Line.new(4.0, 4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for Point/Line"
end
if not (Intersect.new(Point.new(1.0, 8.0), Line.new(4.0, 4.0)).eval_prog([]) == Point.new(1.0, 8.0))
  puts "Intersect test #2 fails for Point/Line"
end
if not (Intersect.new(Point.new(5.0, 4.0), VerticalLine.new(4.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for Point/VerticalLine"
end
if not (Intersect.new(Point.new(4.0, 4.0), VerticalLine.new(4.0)).eval_prog([]) == Point.new(4.0, 4.0))
  puts "Intersect test #2 fails for Point/VerticalLine"
end
if not (Intersect.new(Point.new(2.0, 2.0), LineSegment.new(1.0, 1.0, 4.0, 4.0)).eval_prog([]) == Point.new(2.0, 2.0))
  puts "Intersect test #1 fails for Point/LineSegment"
end
if not (Intersect.new(Point.new(4.1, 4.1), LineSegment.new(1.0, 1.0, 4.0, 4.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #2 fails for Point/LineSegment"
end

if not (Intersect.new(Line.new(4.0, 4.0), Point.new(1.0, 8.0)).eval_prog([]) == Point.new(1.0, 8.0))
  puts "Intersect test #1 fails for Line/Point"
end
if not (Intersect.new(Line.new(4.0, 4.0), Point.new(4.0, 4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #2 fails for Line/Point"
end
if not (Intersect.new(Line.new(4.0, 4.0), Line.new(4.0, 4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for Line/Line"
end
if not (Intersect.new(Line.new(1.0, 7.0), Line.new(4.0, 4.0)).eval_prog([]) == Point.new(1.0, 8.0))
  puts "Intersect test #2 fails for Line/Line"
end
if not (Intersect.new(Line.new(4.0, 4.0), VerticalLine.new(4.0)).eval_prog([]) == Point.new(4.0, 20.0))
  puts "Intersect test fails for Line/VerticalLine"
end
if not (Intersect.new(Line.new(-1.0, 1.0), LineSegment.new(1.0, 1.0, 4.0, 4.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for Line/LineSegment"
end
if not (Intersect.new(Line.new(-1.0, 2.0), LineSegment.new(1.0, 1.0, 4.0, 4.0)).eval_prog([]) == Point.new(1.0, 1.0))
  puts "Intersect test #2 fails for Line/LineSegment"
end

if not (Intersect.new(VerticalLine.new(4.0), Point.new(4.0, 8.0)).eval_prog([]) == Point.new(4.0, 8.0))
  puts "Intersect test #1 fails for VerticalLine/Point"
end
if not (Intersect.new(VerticalLine.new(4.0), Point.new(4.1, 4.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #2 fails for VerticalLine/Point"
end
if not (Intersect.new(VerticalLine.new(4.0), Line.new(4.0, 4.0)).eval_prog([]) == Point.new(4.0, 20.0))
  puts "Intersect test fails for VerticalLine/Line"
end
if not (Intersect.new(VerticalLine.new(4.0), VerticalLine.new(4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for VerticalLine/VerticalLine"
end
if not (Intersect.new(VerticalLine.new(4.0), VerticalLine.new(4.0)).eval_prog([]) == VerticalLine.new(4.0))
  puts "Intersect test #2 fails for VerticalLine/VerticalLine"
end
if not (Intersect.new(VerticalLine.new(4.1), LineSegment.new(1.0, 1.0, 4.0, 4.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for VerticalLine/LineSegment"
end
if not (Intersect.new(VerticalLine.new(2.0), LineSegment.new(1.0, 1.0, 4.0, 4.0)).eval_prog([]) == Point.new(2.0, 2.0))
  puts "Intersect test #2 fails for VerticalLine/LineSegment"
end

# Intersection tests with LineSegment and Point/Line/VerticalLine
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), Point.new(2.0, 2.0)).eval_prog([]) == Point.new(2.0, 2.0))
  puts "Intersect test #1 fails for LineSegment/Point"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), Point.new(4.1, 4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #2 fails for LineSegment/Point"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), Line.new(-1.0, 1.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for LineSegment/Line"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), Line.new(-1.0, 2.0)).eval_prog([]) == Point.new(1.0, 1.0))
  puts "Intersect test #2 fails for LineSegment/Line"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), VerticalLine.new(4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for LineSegment/VerticalLine"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), VerticalLine.new(2.0)).eval_prog([]) == Point.new(2.0, 2.0))
  puts "Intersect test #2 fails for LineSegment/VerticalLine"
end

# Intersection between a vertical LineSegment and Point/Line/VerticalLine
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), Point.new(1.0, 2.0)).eval_prog([]) == Point.new(1.0, 2.0))
  puts "Intersect test #3 fails for LineSegment/Point"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), Point.new(1.0, 4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #4 fails for LineSegment/Point"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), Line.new(-1.0, 1.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #3 fails for LineSegment/Line"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), Line.new(-1.0, 4.0)).eval_prog([]) == Point.new(1.0, 3.0))
  puts "Intersect test #4 fails for LineSegment/Line"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), VerticalLine.new(4.1)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #3 fails for LineSegment/VerticalLine"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), VerticalLine.new(1.0)).eval_prog([]) == LineSegment.new(1.0, 1.0, 1.0, 4.0))
  puts "Intersect test #4 fails for LineSegment/VerticalLine"
end

# intersection between two oblique LineSegments
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), LineSegment.new(4.1, 4.1, 5.0, 5.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #1 fails for LineSegment/LineSegment"
end
=end

xx1 = LineSegment.new(1.0, 1.0, 4.0, 4.0)
xx2 = LineSegment.new(2.0, 2.0, 3.0, 3.0)
xx12_res = Intersect.new(xx1,xx2).eval_prog([])
if not (xx12_res == xx2)
  puts "Intersect test #2 fails for LineSegment/LineSegment, res = " + xx12_res.to_s
end


if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), LineSegment.new(-1.0, -1.0, 3.0, 3.0)).eval_prog([]) == LineSegment.new(1.0, 1.0, 3.0, 3.0))
  puts "Intersect test #3 fails for LineSegment/LineSegment"
end
xx3 = LineSegment.new(2.0, 2.0, 5.0, 5.0)
xx4 =  LineSegment.new(2.0, 2.0, 4.0, 4.0)
xx34_res = Intersect.new(xx1, xx3).eval_prog([])
if not ( xx34_res == xx4)
  puts "Intersect test #4 fails for LineSegment/LineSegment, res = " + xx34_res.to_s
end


if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), LineSegment.new(4.0, 4.0, 5.0, 5.0)).eval_prog([]) == Point.new(4.0, 4.0))
  puts "Intersect test #5 fails for LineSegment/LineSegment"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 4.0, 4.0), LineSegment.new(-4.0, -4.0, 1.0, 1.0)).eval_prog([]) == Point.new(1.0, 1.0))
  puts "Intersect test #6 fails for LineSegment/LineSegment"
end
if not (Intersect.new(LineSegment.new(2.0, 2.0, 3.0, 3.0), LineSegment.new(1.0, 1.0, 5.0, 5.0)).eval_prog([]) == LineSegment.new(2.0, 2.0, 3.0, 3.0))
  puts "Intersect test #7 fails for LineSegment/LineSegment"
end

# intersection between two vertical LineSegments
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), LineSegment.new(1.0, 4.1, 1.0, 5.0)).eval_prog([]).class == NoPoints.new.class)
  puts "Intersect test #8 fails for LineSegment/LineSegment"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), LineSegment.new(1.0, 2.0, 1.0, 3.0)).eval_prog([]) == LineSegment.new(1.0, 2.0, 1.0, 3.0))
  puts "Intersect test #9 fails for LineSegment/LineSegment"
  puts Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), LineSegment.new(1.0, 2.0, 1.0, 3.0)).eval_prog([]).class
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), LineSegment.new(1.0, -1.0, 1.0, 3.0)).eval_prog([]) == LineSegment.new(1.0, 1.0, 1.0, 3.0))
  puts "Intersect test #10 fails for LineSegment/LineSegment"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), LineSegment.new(1.0, 2.0, 1.0, 5.0)).eval_prog([]) == LineSegment.new(1.0, 2.0, 1.0, 4.0))
  puts "Intersect test #11 fails for LineSegment/LineSegment"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), LineSegment.new(1.0, 4.0, 1.0, 5.0)).eval_prog([]) == Point.new(1.0, 4.0))
  puts "Intersect test #12 fails for LineSegment/LineSegment"
end
if not (Intersect.new(LineSegment.new(1.0, 1.0, 1.0, 4.0), LineSegment.new(1.0, -4.0, 1.0, 1.0)).eval_prog([]) == Point.new(1.0, 1.0))
  puts "Intersect test #136 fails for LineSegment/LineSegment"
end
if not (Intersect.new(LineSegment.new(1.0, 2.0, 1.0, 3.0), LineSegment.new(1.0, 1.0, 1.0, 5.0)).eval_prog([]) == LineSegment.new(1.0, 2.0, 1.0, 3.0))
  puts "Intersect test #14 fails for LineSegment/LineSegment"
end
