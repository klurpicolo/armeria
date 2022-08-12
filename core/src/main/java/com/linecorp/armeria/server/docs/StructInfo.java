/*
 * Copyright 2015 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.armeria.server.docs;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;

import com.linecorp.armeria.common.annotation.Nullable;
import com.linecorp.armeria.common.annotation.UnstableApi;

/**
 * Metadata about a struct type.
 */
@UnstableApi
public final class StructInfo implements NamedTypeInfo {

    private final String name;
    private final List<FieldInfo> fields;
    @Nullable
    private final DescriptionInfo descriptionInfo;

    /**
     * Creates a new instance.
     */
    public StructInfo(String name, Iterable<FieldInfo> fields) {
        this(name, fields, null);
    }

    /**
     * Creates a new instance.
     */
    public StructInfo(String name, Iterable<FieldInfo> fields, @Nullable DescriptionInfo descriptionInfo) {
        this.name = requireNonNull(name, "name");
        this.fields = ImmutableList.copyOf(requireNonNull(fields, "fields"));
        this.descriptionInfo = descriptionInfo;
    }

    @Override
    public String name() {
        return name;
    }

    /**
     * Returns the metadata about the fields of the type.
     */
    @JsonProperty
    public List<FieldInfo> fields() {
        return fields;
    }

    /**
     * Returns the description information of this struct.
     */
    @JsonProperty
    @Override
    @JsonInclude(Include.NON_NULL)
    @Nullable
    public DescriptionInfo descriptionInfo() {
        return descriptionInfo;
    }

    @Override
    public Set<TypeSignature> findNamedTypes() {
        final Set<TypeSignature> collectedNamedTypes = new HashSet<>();
        fields().forEach(f -> ServiceInfo.findNamedTypes(collectedNamedTypes, f.typeSignature()));
        return ImmutableSortedSet.copyOf(comparing(TypeSignature::name), collectedNamedTypes);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof StructInfo)) {
            return false;
        }

        final StructInfo that = (StructInfo) o;
        return name.equals(that.name) && fields.equals(that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fields);
    }

    @Override
    public String toString() {
        return name;
    }
}
