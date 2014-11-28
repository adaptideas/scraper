---
title: Real World Examples for The Lazy
---


This is a compilation of examples extracted from http://www.procurandocursos.com , the project from where we extracted scraper's code.

## Simple Matching

    <td bgcolor="#eeeeee"><p align="justify">${description}</p>

## Split Example

    <div align="center"><br>${name} -...<br>
    <split>
    <div align="center"><p>${name}<br>
    <split>
    <div align="center">${name}</div>

The same information (the Course name) was provided on three different ways across the site.

## Ignoring the end point

    <h2>Carga Hor&aacute;ria</h2> ${duration} horas...

This is kind of trick. It first will match the h2 tag. After that, will capture anything until the string horas is found. Then will ignore everything after. That /h2 is optional.

## Attribute To determine begin point

    <img src="/img/gif/cx_seta_local_site.gif">${name}</span>

## Problems with encoding

    <b>Descri...</b> ${description}</p>

Sometimes, the crawler we are using (http://projetos.vidageek.net/crawler) is not able to understand the encoding (even using IBM icu4j). So, sometimes we use the ellipsis matcher to ignore some chars.

## Too Lazy to get the tag structure

    <body>...CARGA HOR&Aacute;RIA ${duration} Local ...

Sometimes the html we had to parse was so hideous that we would simply give up. Other times, we came to workarounds like this. Find `<body>`, ignore everything until find "CARGA HOR&Aacute;RIA", get everything until "Local" and them ignore everything after.

## Adding tags that are on the template

    <a class="black10b">Descri&ccedil;&atilde;o</a>
    ${description}
    <a></a>

On this case, we had to use the ::a:: tag to match. Since it appears on the template, we had to add the tag to anywhere it appeared. Those /a are probably dispensable.

## Multiple capture groups

    <title>${name}</title>
    <div class="summary">
    <strong></strong>${duration}

On the project, we usually use a single template for any data we want to extract. But sometimes it's much easier to use the same template and simply add another capture group.
