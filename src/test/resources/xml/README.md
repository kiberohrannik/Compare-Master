# XML Manual Test Fixtures

These files are intended for manual testing of CompareMaster XML actions.

- `01_format_inline_messy.xml`: Run `Format` then `Inline`.
- `02_sort_children_unsorted.xml`: Run `Sort` and verify child elements are re-ordered.
- `03_sort_same_tag_attr_order.xml`: Run `Sort` to test same-tag sorting with attributes.
- `04_escape_input_raw.xml`: Run `Edit -> Escape`.
- `05_unescape_input_escaped.xml`: Run `Edit -> Unescape`.
- `06_doctype_unsupported.xml`: Run `Format` / `Inline` / `Sort` to verify DOCTYPE unsupported error path.
- `07_namespaces_mixed.xml`: Namespace handling with `Format`/`Sort`.
- `08_cdata_and_text.xml`: CDATA preservation behavior.
- `09_invalid_unclosed_tag.xml`: Invalid XML error path for formatter/sorter.
- `10_blank.xml`: Blank input behavior.

Note: `Edit -> Replace field values from left to right` and
`Edit -> Add absent values from left to right` are currently unsupported for XML
and should show an "Unsupported Function for XML" warning.
