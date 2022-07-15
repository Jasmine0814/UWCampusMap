/*
 * Copyright (C) 2022 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Summer Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package sets;

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {

  // TODO: Include a representation invariant (RI) for the fields below
  // RI: points != null
  // TODO: Include a abstraction function (AF) for this ADT
  // The abstraction function is when this.complement is true, it is an immutable
  // set of points. otherwise, it is the complement of immutable set of points.
  private final boolean complement;
  private final FiniteSet points;

  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    this(false, FiniteSet.of(vals));
  }

  public SimpleSet(boolean b, FiniteSet s) {
    this.complement = b;
    this.points = s;
  }

  // NOTE: feel free to create other constructors

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;
    return this.complement == other.complement
            && this.points.equals(other.points);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    if(!complement) {
      return this.points.size();
    } else {
      return Float.POSITIVE_INFINITY;
    }
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers. These
   *     floats will be turned into strings in the standard manner (the same as
   *     done by, e.g., String.valueOf(float)).
   */
  public String toString() {
    StringBuilder result = new StringBuilder();
    if(points.size() == 0 && complement) {
      result.append("R");
    } else if (points.size() == 0) {
      result.append("{}");
    } else {
      if(complement) {
        result.append("R \\ ");
      }
        result.append("{");
        List<Float> pointList = points.getPoints();
        for(int i = 0; i < pointList.size() - 1; i++) {
          result.append(pointList.get(i) + ", ");
        }
        result.append(pointList.get(pointList.size() - 1));
        result.append("}");
    }
    return String.valueOf(result);
  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    // When the set is not the complement of the finite set, the variable complement(boolean)
    // will be true. And then the parameter of constructor would also be true. Return the new
    // SimpleSet with the complement(true) and points. Otherwise it has false for complement.
    if(!complement) {
      return new SimpleSet(true, this.points);
    } else {
      return new SimpleSet(false, this.points);
    }
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    // union two complements of finite set which is the complement of intersection of
    // these two set. For example, the union of R\ {1,2,3} and R\ {2,3,4} should be
    // R\{2, 3} so it is complement and call the intersection method.
    if(this.complement && other.complement) {
      return new SimpleSet(true, this.points.intersection(other.points));

    // union two finite set which are not complement.
    // For example, the union of {1,2,3} and {2,3,4} should be {1,2,3,4} so it is not
    // complement and call the union method to union these two finite set.
    } else if (!this.complement && !other.complement) {
      return new SimpleSet(false, this.points.union(other.points));

    // union this set and other complement set. For example {1,2,3} and R\{2,3,4}
    // the union of these two should be R\{4} where {4} is the difference between
    // other and this. Therefore it is complement(true) and the finite set is other
    // points difference this points.
    } else if (!this.complement && other.complement) {
      return new SimpleSet(true, other.points.difference(this.points));

    // union ths complement set and other set. For example R\{1,2,3} and {2,3,4}
    // the answer should be R\{1} where {1} is the difference between this and other
    // Therefore it is complement{true} and the finite set is this points difference
    // other points
    } else {
      return new SimpleSet(true, this.points.difference(other.points));
    }
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersect other
   */
  public SimpleSet intersection(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct

    // intersection of two complement of finite sets which is the union of this.point
    // and other.point. For Example R\{1,2,3} and R\{2,3,4}. the intersection of these
    // two variable should be R\{1,2,3,4} where {1,2,3,4} is the union of two variable
    // Therefore it is complement(true) and the finite set is the union of these two.
    if(this.complement && other.complement) {
      return new SimpleSet(true, this.points.union(other.points));

    // intersection of two finite sets
    // For example, the intersection of {1,2,3} and {2,3,4} should be {2,3} so it is
    // not complement(false) and call intersection method to ge the interction of these
    // two finite sets.
    } else if (!this.complement && !other.complement) {
      return new SimpleSet(false, this.points.intersection(other.points));

      // intersection of this finite set and other complement of finite set. For example,
      // {1,2,3} and R\{2,3,4}, the answer should be {1} where it is the difference between
      // this points and other points. Therefore it is not complement(false) and the finite
      // set is this difference other.
    } else if (!this.complement && other.complement) {
      return new SimpleSet(false, this.points.difference(other.points));

      // intersection of this complement of finite set and other finite set. For example,
      // R\{1,2,3} and {2,3,4}, the answer should be {4} where it is the difference between
      // other points and this points. Therefore it is not complement(false) and the finite set
      // is the other difference this.
    } else {
      return new SimpleSet(false, other.points.difference(this.points));
    }
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct

    // the difference between two complement of finite sets which is the difference
    // between other and this. For example, R\{1,2,3} and R\{2,3,4} the difference betwenn
    // these two should be {4} (because this have it but other do not have) where it is
    // the difference between other and this. Therefore it is not complement(false) and
    // the finite set should be other points difference this points.
    if(this.complement && other.complement) {
      return new SimpleSet(false, other.points.difference(this.points));

      // the difference between two finite set.
      // For example, the difference between {1,2,3} and {2,3,4} should be {1}. So it is
      // not complement(false) and called difference method to get difference between
      // this points and other points
    } else if (!this.complement && !other.complement) {
      return new SimpleSet(false, this.points.difference(other.points));

      // the difference between this finite set and other complement of finite set
      // For example, the difference between {1,2,3} and R\{2,3,4} should be {2,3}
      // (this have it but other do not have) which is the intersection of these two finite
      // set. Therefore it is not complement(false) and the finite set should be the
      // intersection of these two.
    } else if (!this.complement && other.complement) {
      return new SimpleSet(false, this.points.intersection(other.points));

      // the difference between this complement of finite set and other finite set.
      // For example, the difference between R\{1,2,3} and {2,3,4} should be R\{1,2,3,4}
      // which means R\{1,2,3} have but {2,3,4}don't have. {1,2,3,4} comes from the union of
      // {1,2,3} and {2,3,4}. Therefore it is complement(true) and the finite set should
      // be the union of these two.
    } else {
      return new SimpleSet(true, this.points.union(other.points));
    }
  }

}
