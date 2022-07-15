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

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * FiniteSetTest is a glassbox test of the FiniteSet class.
 */
public class FiniteSetTest {

  /** Test creating sets. */
  @Test
  public void testCreation() {
    assertEquals(Arrays.asList(),
        FiniteSet.of(new float[0]).getPoints());         // empty
    assertEquals(Arrays.asList(1f),
        FiniteSet.of(new float[] {1}).getPoints());      // one item
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {1, 2}).getPoints());   // two items
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {2, 1}).getPoints());   // two out-of-order
    assertEquals(Arrays.asList(-2f, 2f),
        FiniteSet.of(new float[] {2, -2}).getPoints());  // negative
  }

  // Some example sets used by the tests below.
  private static FiniteSet S0 = FiniteSet.of(new float[0]);
  private static FiniteSet S1 = FiniteSet.of(new float[] {1});
  private static FiniteSet S12 = FiniteSet.of(new float[] {1, 2});

  /** Test set equality. */
  @Test
  public void testEquals() {
    assertTrue(S0.equals(S0));
    assertFalse(S0.equals(S1));
    assertFalse(S0.equals(S12));

    assertFalse(S1.equals(S0));
    assertTrue(S1.equals(S1));
    assertFalse(S1.equals(S12));

    assertFalse(S12.equals(S0));
    assertFalse(S12.equals(S1));
    assertTrue(S12.equals(S12));
  }

  /** Test set size. */
  @Test
  public void testSize() {
    assertEquals(S0.size(), 0);
    assertEquals(S1.size(), 1);
    assertEquals(S12.size(), 2);
  }

  // TODO: Feel free to initialize (private static) FiniteSet objects here
  //       if you plan to use them for the tests below.
  private static FiniteSet SNull = FiniteSet.of(new float[] {});
  private static FiniteSet SNull1 = FiniteSet.of(new float[] {});
  private static FiniteSet S2 = FiniteSet.of(new float[] {2});
  private static FiniteSet S3 = FiniteSet.of(new float[] {3});
  private static FiniteSet S23 = FiniteSet.of(new float[] {2, 3});
  private static FiniteSet S34 = FiniteSet.of(new float[]{3, 4});

  private static FiniteSet S123 = FiniteSet.of(new float[] {1, 2, 3});
  private static FiniteSet S1234 = FiniteSet.of(new float[] {1, 2, 3, 4});

  /** Tests forming the union of two finite sets. */
  @Test
  public void testUnion() {
    assertEquals(SNull.union(SNull1), SNull);
    assertEquals(SNull.union(S3), S3);
    assertEquals(S3.union(SNull), S3);
    assertEquals(S12.union(S23), S123);
    assertEquals(S12.union(S34), S1234);
    assertEquals(S123.union(S1234), S1234);
  }

  /** Tests forming the intersection of two finite sets. */
  @Test
  public void testIntersection() {
    assertEquals(SNull.intersection(SNull1), SNull1);
    assertEquals(SNull.intersection(S3), SNull);
    assertEquals(S3.intersection(SNull), SNull);
    assertEquals(S12.intersection(S23), S2);
    assertEquals(S23.intersection(S34), S3);
    assertEquals(S123.intersection(S1234), S123);

  }

  /** Tests forming the difference of two finite sets. */
  @Test
  public void testDifference() {
    assertEquals(SNull.difference(SNull1), SNull1);
    assertEquals(SNull.difference(S3), SNull);
    assertEquals(S3.difference(SNull), S3);
    assertEquals(S12.difference(S34), S12);
    assertEquals(S12.difference(S23), S1);
    assertEquals(S12.difference(S123), SNull1);
  }

}
