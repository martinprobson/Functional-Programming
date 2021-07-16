
--
-- Pointless composition
--
fn :: (RealFrac a, Integral b, Floating a) => a -> b
fn x = ceiling (negate (tan (cos (max 50 x))))
--
-- Using function composition rule
-- (replace all open brackets with '.' and the innermost function second argument with '$'
fn' :: (RealFrac a, Integral b, Floating a) => a -> b
fn' x = ceiling . negate . tan . cos . max 50 $ x
--
-- Because x is mentioned on both sides, we can use currying to simplify further.
-- This is called pointless composition.
-- becomes: -
fn'' :: (RealFrac a, Integral b, Floating a) => a -> b
fn'' = ceiling . negate . tan . cos . max 50
--
--
main = do
    print $ fn 34.4
    print $ fn' 34.4
    print $ fn'' 34.4
