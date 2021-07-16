# Scala with CATS

## Preface
Monads, functors and other functional programming patterns are the FP equivalent of OO design patterns - architectural building blocks
that turn up again and again in code. The differ from OO patterns in two main ways: -
* they are formally, and thus precisely defined; and
* they are extremely general.
The means they can be difficult to understand.

## 1. Introduction
Cats contains a wide variety of FP tools allowing developers to pick and choose the ones they want to use. The majority of these tools are delivered
in the form of *type classes* that we can apply to existing Scala types.

### Anatomy of a Type Class
There are three components to a type class pattern in Scala: -
1. The *type class* itself.
2. *Instances* of the type class for a particular type.
3. The *interface* method we expose to users.


