fmap' :: (a -> b) -> [a] -> [b]
fmap' _ [] = []
fmap' f (x:xs) = f x : fmap' f xs

fmap'' :: (a -> b) -> Maybe a -> Maybe b
fmap'' _ Nothing = Nothing
fmap'' f (Just a) = Just (f a)
--
amap'' :: Maybe (a -> b) -> Maybe a -> Maybe b
amap'' Nothing _ = Nothing
amap'' _ Nothing = Nothing
amap'' (Just f) (Just a) = Just (f a)
