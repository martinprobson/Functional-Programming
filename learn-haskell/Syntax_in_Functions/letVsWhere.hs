--
-- Surface area of a cylinder with where statement
--
cylinder :: (RealFloat a) => a -> a -> a
cylinder r h = sideArea + 2 * topArea
  where sideArea = 2 * pi * r * h
        topArea = pi * r^2
--
-- Surface area of a cylinder with let statement
--
cylinder' :: (RealFloat a) => a -> a -> a
cylinder' r h = 
  let sideArea = 2 * pi * r * h
      topArea = pi * r^2
   in sideArea +2 * topArea
