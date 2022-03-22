package kz.reself.resod.api.model

enum class AdState(name: String) {
    New("New"),
    Needs_Repair("Needs_Repair"),
    No_Need_Repair("No_Need_Repair"),
    Needs_Cosmetic_Repair("Needs_Cosmetic_Repair"),
    Draft("Draft"),
    Inactive("Inactive"),
    Deleted("Deleted");
}