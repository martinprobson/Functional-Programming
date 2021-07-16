
double :: (Integral a) => a -> a 
double n = 2 * n

double' :: (Monad m) => (m a) -> a
double' m a = a
