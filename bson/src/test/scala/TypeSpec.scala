/*
 * Copyright 2013 Stephane Godbillon
 * @sgodbillon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.specs2.mutable._
import reactivemongo.bson._

class TypeSpec extends Specification {
  "BSON types" title

  section("unit")

  "BSON document" should {
    "be empty" in {
      BSONDocument().elements must beEmpty and (
        BSONDocument.empty.elements must beEmpty) and (
          document.elements must beEmpty) and (
            document().elements must beEmpty) and (
              BSONDocument.empty.contains("foo") must beFalse)
    }

    "be created with a new element " in {
      val doc = BSONDocument.empty :~ ("foo" -> 1)

      doc must_=== BSONDocument("foo" -> 1) and (
        doc.contains("foo") must beTrue)
    }

    "remove specified elements" in {
      val doc = BSONDocument("Foo" -> 1, "Bar" -> 2, "Lorem" -> 3)

      doc.remove("Bar", "Lorem") must_=== BSONDocument("Foo" -> 1) and (
        doc -- ("Foo", "Bar") must_=== BSONDocument("Lorem" -> 3))
    }
  }

  "BSONArray" should {
    "be empty" in {
      BSONArray().values must beEmpty and (
        BSONArray.empty.values must beEmpty) and (
          array.values must beEmpty) and (
            array().values must beEmpty)

    }

    "be created with a new element " in {
      val arr = BSONArray.empty ++ ("foo", "bar")

      arr must_=== BSONArray("foo", "bar")
    }

    "be returned with a prepended element" in {
      BSONString("bar") +: BSONArray("foo") must_=== BSONArray("bar", "foo")
    }
  }

  "BSONBinary" should {
    "be read as byte array" in {
      val bytes = Array[Byte](1, 2, 3)
      val bson = BSONBinary(bytes, Subtype.GenericBinarySubtype)

      bson.byteArray aka "read #1" must_== bytes and (
        bson.byteArray aka "read #2" must_== bytes)
    }

    "be created from UUID" in {
      val uuid = java.util.UUID.fromString(
        "b32e4733-0679-4dd3-9978-230e70b55dce")

      val expectedBytes = Array[Byte](
        -77, 46, 71, 51, 6, 121, 77, -45, -103, 120, 35, 14, 112, -75, 93, -50)

      BSONBinary(uuid) must_=== BSONBinary(expectedBytes, Subtype.UuidSubtype)
    }
  }

  "BSONTimestamp" should {
    "extract time and ordinal values" in {
      val ts = BSONTimestamp(6065270725701271558L)

      ts.value aka "raw value" must_== 6065270725701271558L and (
        ts.time aka "time" must_== 1412180887L) and (
          ts.ordinal aka "ordinal" must_== 6)
    }

    "be created from the time and ordinal values" in {
      BSONTimestamp(1412180887L, 6) must_== BSONTimestamp(6065270725701271558L)
    }
  }

  section("unit")
}
