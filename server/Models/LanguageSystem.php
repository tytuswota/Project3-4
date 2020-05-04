<?php


class LanguageSystem
{
    static function getTranslation($request)
    {
        $translationID = $request->id; // for instance: "TextPinWrong"
        $language = $request->language; // for instance: "NEDERLANDS", "ENGLISH"

        // DO query
        $Result[$request->id] = "TestString";
        return $Result;
    }
}