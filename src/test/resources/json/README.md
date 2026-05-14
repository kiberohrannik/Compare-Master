# JSON Manual Test Fixtures

These files are intended for manual testing of CompareMaster JSON actions.

- `01_format_inline_messy.json`: Run `Format` then `Inline`.
- `02_sort_nested_unsorted.json`: Run `Sort` and check object keys are alphabetically ordered.
- `03_replace_values_left_target.json` + `04_replace_values_right_source.json`:
  - Load left file in left editor, right file in right editor.
  - Run `Edit -> Replace field values from left to right`.
  - Expected: existing fields in right are updated to match left; right-only fields remain.
- `05_add_absent_left_target.json` + `06_add_absent_right_source.json`:
  - Run `Edit -> Add absent values from left to right`.
  - Expected: missing array items are added in right; non-array scalar value differences remain unchanged.
- `07_escape_input_raw.json`: Run `Edit -> Escape`.
- `08_unescape_input_escaped.json`: Run `Edit -> Unescape`.
- `09_double_escaped_for_unescape_twice.json`: Run `Unescape` twice.
- `10_invalid_trailing_comma.json`: Use for invalid JSON handling during `Format` / `Sort`.
- `11_array_root_left_target.json` + `12_array_root_right_source.json`:
  - Use with `Add absent values` and `Replace field values`.
- `13_blank.json`: Blank input behavior for actions.
