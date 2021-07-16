data Vector a = Vector a a a deriving (Show)

vplus :: (Num t) => Vector t -> Vector t -> Vector t
(Vector i j z) `vplus` (Vector l m n) = Vector (i+l) (j+m) (z+n)

vmult :: (Num t) => Vector t -> t -> Vector t
(Vector i j z) `vmult`  m = Vector (i*m) (j*m) (z*m)
